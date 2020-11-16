package com.damiannguyen.GW2GuildHelper.core.security;

import com.damiannguyen.GW2GuildHelper.modules.users.User;
import com.damiannguyen.GW2GuildHelper.modules.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserHelper {
    @Autowired
    UserRepository userRepository;

    public User getUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }else{
            username = principal.toString();
        }

        return userRepository.findByUsername(username);
    }
}
