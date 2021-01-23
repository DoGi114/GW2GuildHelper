package com.damiannguyen.GW2GuildHelper.modules.users;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.GuildRepository;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final GuildRepository guildRepository;
    private final BCryptPasswordEncoder encoder;


    @GetMapping("/register")
    public String register(Model model){
        List<Guild> guilds = guildRepository.findAll();
        model.addAttribute("guilds", guilds);
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password_confirm") String passwordConfirm,
            @RequestParam("guild") String guildName,
            @RequestParam("guild-cbx") String guildNameCbx
    ) {
        if(userService.isUserNotRegistered(username)) {
            if (password.equals(passwordConfirm)) {
                if(guildNameCbx.equals("None")) {
                    userService.createUser(username, email, guildName, password);
                }else{
                    userService.createUser(username, email, guildNameCbx, password);
                }
                return "redirect:/app/welcome";
            } else {
                return "redirect:/register-error?badPassword=true&exists=false";
            }
        }else {
            return "redirect:/register-error?exists=true";
        }
    }

    @GetMapping("/register-error")
    public String registerError(
            @RequestParam("exists") boolean exists,
            @RequestParam("badPassword") boolean badPassword,
            Model model){
        model.addAttribute("exists", exists);
        model.addAttribute("badPassword", badPassword);
        return "register";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/reset-request")
    public String getRequestResetPassword(){
        return "reset-request";
    }

    @PostMapping("/reset-request")
    public String requestResetPassword(
            @RequestParam("email") String email
    ){
        User user = userRepository.findByEmail(email);
        user.setUuid(UUID.randomUUID().toString());
        userRepository.save(user);
        userService.remindPassword(user.getUsername());
        return "login";
    }

    @GetMapping("/reset-password/{uuid}")
    public String getResetPassword(@PathVariable("uuid") String uuid, Model model){
        model.addAttribute("uuid", uuid);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam("uuid") String uuid,
            @RequestParam("password") String password,
            @RequestParam("confirm-password") String confirmPassword,
            RedirectAttributes redirectAttributes
    ){
        if(!password.equals(confirmPassword))
            redirectAttributes.addFlashAttribute("error", "Passwords doesnt match");

        if(uuid.equals("")) return "login";

        User user = userRepository.findByUuid(uuid);
        user.setPassword(encoder.encode(password));
        user.setPasswordConfirm(encoder.encode(confirmPassword));
        user.setUuid("");
        userRepository.save(user);

        userService.remindPassword(user.getUsername());

        return "login";
    }

}
