package com.damiannguyen.GW2GuildHelper.modules.tasks;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.GuildRepository;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogPojo;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogRepository;
import com.damiannguyen.GW2GuildHelper.modules.mappers.LogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class GuildLogTask {

    private final GuildRepository guildRepository;
    private final LogRepository logRepository;
    private final LogMapper logMapper;

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

                LogPojo[] logPojos = response.getBody();
                //TODO: Assert not null
                for (LogPojo logPojo : logPojos) {
                    Log log = logMapper.map(logPojo, guild);
                    logRepository.save(log);
                }
                log.info("Guild " + guild.getName() + " updated!");
            }catch (HttpClientErrorException exception){
                if(exception.getStatusCode() == HttpStatus.UNAUTHORIZED){
                    log.error("Failed to log in guild " + guild.getName() + " due to bad access_token");
                }
            }catch (HttpServerErrorException exception){
                if(exception.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                    log.error("API is not responding@");
                }
            }
        }
    }
}
