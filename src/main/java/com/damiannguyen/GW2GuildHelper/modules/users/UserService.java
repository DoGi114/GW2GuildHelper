package com.damiannguyen.GW2GuildHelper.modules.users;

import com.damiannguyen.GW2GuildHelper.core.mailing.MailService;
import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.GuildRepository;
import com.damiannguyen.GW2GuildHelper.modules.users.role.Role;
import com.damiannguyen.GW2GuildHelper.modules.users.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final GuildRepository guildRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;

    public boolean isPasswordSame(String password1, String password2){
//        return password1.equals(password2);
        return encoder.matches(password2, password1);
    }

    public void createUser(String username, String email, String guildName, String password) {
        Guild guild = guildRepository.findByName(guildName);
        if (guild == null) {
            guild = new Guild(guildName);
        }
        Role role;

        if (guildRepository.findByName(guild.getName()) == null) {
            guildRepository.save(guild);
            role = roleRepository.findByName("ADMIN");
        } else {
            role = roleRepository.findByName("USER");
        }
        roleRepository.save(role);
        User user = new User(username, email, encoder.encode(password), guild, role);
        userRepository.save(user);
        mailService.sendGreetings(user);
    }

    public boolean isUserNotRegistered(String username) {
        return !userRepository.existsByUsername(username);
    }

    public void remindPassword(String username) {
        mailService.sendPasswordReseted(userRepository.findByUsername(username));
    }
}
