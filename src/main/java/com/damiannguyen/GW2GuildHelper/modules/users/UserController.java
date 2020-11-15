package com.damiannguyen.GW2GuildHelper.modules.users;


import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.GuildRepository;
import com.damiannguyen.GW2GuildHelper.modules.users.role.Role;
import com.damiannguyen.GW2GuildHelper.modules.users.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GuildRepository guildRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("password_confirm") String passwordConfirm,
            @RequestParam("guild") String guildName
    ) {

        //TODO: Check passwords
        Guild guild = guildRepository.findByName(guildName);
        if(guild == null){
            guild = new Guild(guildName);
        }
        Role role;

        if(guildRepository.findByName(guild.getName()) == null) {
            guildRepository.save(guild);
            role = roleRepository.findByName("ADMIN");
        }else{
            role = roleRepository.findByName("USER");
        }
        roleRepository.save(role);
        userRepository.save(new User(username, encoder.encode(password), guild, role));


        return "redirect:/app/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

}
