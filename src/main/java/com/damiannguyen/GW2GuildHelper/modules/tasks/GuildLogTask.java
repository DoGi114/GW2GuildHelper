package com.damiannguyen.GW2GuildHelper.modules.tasks;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.GuildRepository;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogPojo;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogRepository;
import com.damiannguyen.GW2GuildHelper.modules.mappers.LogMapper;
import com.damiannguyen.GW2GuildHelper.modules.users.User;
import com.damiannguyen.GW2GuildHelper.modules.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class GuildLogTask {

    @Autowired
    private GuildRepository guildRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private LogMapper logMapper;

    private Logger logger = LoggerFactory.getLogger(GuildLogTask.class);

    @Scheduled(fixedRate = 3600000)
    public void getGuildLog(){

        List<Guild> userList = guildRepository.findAll();
        for(Guild guild : userList) {
            String api = "https://api.guildwars2.com/v2/guild/" + guild.getGuildId() + "/log?access_token=" + guild.getLeaderApiKey();
            RestTemplate restTemplate = new RestTemplate();

            try {
                ResponseEntity<LogPojo[]> response =
                        restTemplate.getForEntity(
                                api,
                                LogPojo[].class);

                List<LogPojo> logPojos = Arrays.asList(response.getBody());
                for (LogPojo logPojo : logPojos) {
                    Log log = logMapper.map(logPojo, guild);
                    logRepository.save(log);
                }
                logger.info("Guild " + guild.getName() + " updated!");
            }catch (HttpClientErrorException exception){
                if(exception.getStatusCode() == HttpStatus.UNAUTHORIZED){
                    logger.error("Failed to log in guild " + guild.getName() + " due to bad access_token");
                }
            }catch (HttpServerErrorException exception){
                if(exception.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                    logger.error("API is not responding@");
                }
            }
        }
    }
}
