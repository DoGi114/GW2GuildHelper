package com.damiannguyen.GW2GuildHelper.modules.users;

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

    public boolean isPasswordSame(String password1, String password2){
        return password1.equals(password2);
    }

    public void createUser(String username, String guildName, String password) {
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
        userRepository.save(new User(username, encoder.encode(password), guild, role));
    }
}
