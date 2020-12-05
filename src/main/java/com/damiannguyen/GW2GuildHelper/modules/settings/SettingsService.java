package com.damiannguyen.GW2GuildHelper.modules.settings;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.GuildRepository;
import com.damiannguyen.GW2GuildHelper.modules.users.User;
import com.damiannguyen.GW2GuildHelper.modules.users.UserRepository;
import com.damiannguyen.GW2GuildHelper.modules.users.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingsService {
    private final GuildRepository guildRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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

    public void setGuildAdmin(Guild guild, List<String> adminList){
        List<User> userList = getUsersInGuild(guild);
        for(User user : userList){
            if(adminList.contains(user.getUsername())) {
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
