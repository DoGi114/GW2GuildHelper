package com.damiannguyen.GW2GuildHelper.modules.welcome;

import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogPojo;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogRepository;
import com.damiannguyen.GW2GuildHelper.modules.mappers.LogMapper;
import com.damiannguyen.GW2GuildHelper.modules.tasks.GuildLogTask;
import com.damiannguyen.GW2GuildHelper.modules.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final UserHelper userHelper;
    private final GuildLogTask guildLogTask;

    public String loadLog(){
        User user = userHelper.getUser();
        if(( user.getGuild().getLeaderApiKey() != null && !user.getGuild().getLeaderApiKey().isEmpty() )
                && ( user.getGuild().getGuildId() != null && !user.getGuild().getGuildId().isEmpty() )) {
            guildLogTask.getGuildLog(user.getGuild());
            return "loaded";
        }else{
            return "error";
        }
    }
}
