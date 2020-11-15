package com.damiannguyen.GW2GuildHelper.modules.settings;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.GuildRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class SettingsService {
    @Autowired
    GuildRepository guildRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsService.class);

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
