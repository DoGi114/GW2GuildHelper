package com.damiannguyen.GW2GuildHelper.modules.settings;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.GuildRepository;
import com.damiannguyen.GW2GuildHelper.modules.users.User;
import com.damiannguyen.GW2GuildHelper.modules.users.UserRepository;
import com.damiannguyen.GW2GuildHelper.modules.users.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsService {
    @Autowired
    private GuildRepository guildRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

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

    public void setGuildAdmin(Guild guild, User newAdmin){
        List<User> userList = getUsersInGuild(guild);
        for(User user : userList){
            if(newAdmin.equals(user)){
                user.setRole(roleRepository.getOne(1L));
            }else{
                user.setRole(roleRepository.getOne(2L));
            }
            userRepository.save(user);
        }
    }

    public List<User> getUsersInGuild(Guild guild){
        return userRepository.findAllByGuild(guild);
    }

    public User getGuildAdmin(Guild guild) {
        List<User> userList = getUsersInGuild(guild);
        for(User user : userList){
            if(user.getRole().getName().equalsIgnoreCase("ADMIN")){
                return user;
            }
        }
        //TODO: Bad...
        return null;
    }
}
