package com.damiannguyen.GW2GuildHelper.modules.welcome;

import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.log.LogPojo;
import com.damiannguyen.GW2GuildHelper.modules.log.LogRepository;
import com.damiannguyen.GW2GuildHelper.modules.mappers.LogMapper;
import com.damiannguyen.GW2GuildHelper.modules.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class WelcomeService {
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private LogMapper logMapper;

    public void loadLog(){
        User user = userHelper.getUser();
        String api = "https://api.guildwars2.com/v2/guild/" + user.getGuild().getGuildId() + "/log?access_token=" + user.getGuild().getLeaderApiKey() ;
        RestTemplate restTemplate = new RestTemplate();

        //TODO:Invalid access token

        ResponseEntity<LogPojo[]> response =
                restTemplate.getForEntity(
                        api,
                        LogPojo[].class);

        List<LogPojo> logPojos = Arrays.asList(response.getBody());
        for(LogPojo logPojo : logPojos){
            Log log = logMapper.map(logPojo, user.getGuild());
            logRepository.save(log);
        }
    }
}
