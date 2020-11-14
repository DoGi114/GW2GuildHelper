package com.damiannguyen.GW2GuildHelper.modules.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class SettingsService {
    private static final String URL = "jdbc:mysql://localhost:3306/gw2_guild_helper?serverTimezone=Europe/Warsaw";
    private static final String USER = "guild_helper_user";
    private static final String PASSWORD = "dq9rh1bfg6olp1";

    private Connection conn;
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsService.class);

    public SettingsService() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e){
            LOGGER.error("Could not connect to database. {}", e);
        }
    }

    public String getPersonalApi(){
        String statementString = "SELECT personal_api FROM settings";
        try {
            PreparedStatement statement = conn.prepareStatement(statementString);
            ResultSet rs = statement.executeQuery();
            //TODO: Check resultset if has items
            rs.next();
            return rs.getString(1);
        } catch (SQLException throwables) {
            LOGGER.error("Cant perform statement {}", statementString);
            return "";
        }
    }

    public String getGuildApi(){
        String statementString = "SELECT guild_api FROM settings";
        try {
            PreparedStatement statement = conn.prepareStatement(statementString);
            ResultSet rs = statement.executeQuery();
            //TODO: Check resultset if has items
            rs.next();
            return rs.getString(1);
        } catch (SQLException throwables) {
            LOGGER.error("Cant perform statement {}", statementString);
            return "";
        }
    }

    private int getRecordsCount() throws SQLException {
        ResultSet rs = conn.prepareStatement("SELECT COUNT(*) FROM settings").executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public void setApi(String personalApi, String guildApi){
        int recordsCount;
        try {
            recordsCount = getRecordsCount();
            LOGGER.info(String.valueOf(recordsCount));
            if(recordsCount > 0){
                PreparedStatement statement = conn.prepareStatement("UPDATE settings SET personal_api = ?, guild_api = ? WHERE id = 1");
                statement.setString(1, personalApi);
                statement.setString(2, guildApi);
                int i = statement.executeUpdate();
                LOGGER.info( i > 0 ? "API updated" : "API not updated");
            }else{
                PreparedStatement statement = conn.prepareStatement("INSERT INTO settings VALUES(?,?,?)");
                statement.setInt(1, 1);
                statement.setString(2, personalApi);
                statement.setString(3, guildApi);
                int i = statement.executeUpdate();
                LOGGER.info( i > 0 ? "API updated" : "API not updated");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
