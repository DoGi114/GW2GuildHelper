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
//    private static final String URL = "jdbc:mysql://localhost:3306/gw2_guild_helper?serverTimezone=Europe/Warsaw";
//    private static final String USER = "guild_helper_user";
//    private static final String PASSWORD = "dq9rh1bfg6olp1";
    @Autowired
    GuildRepository guildRepository;

    private Connection conn;
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsService.class);

    public SettingsService() {
//        try {
//            conn = DriverManager.getConnection(URL, USER, PASSWORD);
//        }catch (SQLException e){
//            LOGGER.error("Could not connect to database. {}", e);
//        }
    }

    public String getLeaderApiKey(Guild guild){
       return guild.getLeaderApiKey();
    }

    public String getGuildId(Guild guild){
        return guild.getGuildId();
    }
//TODO: Needed?

//    private int getRecordsCount() throws SQLException {
//        ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM settings").executeQuery();
//        rs.next();
//        return rs.getInt(1);
//    }

    public void setApi(String leaderApiKey, String guildId, Guild guild){
        guild.setLeaderApiKey(leaderApiKey);
        guild.setGuildId(guildId);
        guildRepository.save(guild);
    }

}
