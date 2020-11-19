package com.damiannguyen.GW2GuildHelper.modules.settings;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.GuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    @Autowired
    GuildRepository guildRepository;

    public SettingsService() {
    }

    public String getLeaderApiKey(Guild guild){
       return guild.getLeaderApiKey();
    }

    public String getGuildId(Guild guild){
        return guild.getGuildId();
    }

    public void setApi(String leaderApiKey, String guildId, Guild guild){
        guild.setLeaderApiKey(leaderApiKey);
        guild.setGuildId(guildId);
        guildRepository.save(guild);
    }

}
