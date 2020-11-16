package com.damiannguyen.GW2GuildHelper.modules.roaster;

import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.guild.member.GuildMember;
import com.damiannguyen.GW2GuildHelper.modules.users.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RoasterService {
    @Autowired
    UserHelper userHelper;
    public List<GuildMember> getRoaster(){

        User user = userHelper.getUser();
        String api = "https://api.guildwars2.com/v2/guild/" + user.getGuild().getGuildId() + "/members?access_token=" + user.getGuild().getLeaderApiKey() ;
        RestTemplate restTemplate = new RestTemplate();

        //TODO:Invalid access token

        ResponseEntity<GuildMember[]> response =
                restTemplate.getForEntity(
                        api,
                        GuildMember[].class);

        return Arrays.asList(response.getBody());
    }
}
