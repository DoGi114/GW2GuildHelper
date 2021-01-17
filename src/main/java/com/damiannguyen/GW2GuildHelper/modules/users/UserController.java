package com.damiannguyen.GW2GuildHelper.modules.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password_confirm") String passwordConfirm,
            @RequestParam("guild") String guildName
    ) {
        if(userService.isUserNotRegistered(username)) {
            if (userService.isPasswordSame(password, passwordConfirm)) {
                userService.createUser(username, email, guildName, password);
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
//        model.addAttribute("user", );
        return "login";
    }

    @GetMapping("/reminded")
    public String reminded(boolean error,
            Model model){
        model.addAttribute("reminded", !error);
        return "login";
    }

    @GetMapping("/remind")
    public String remindPassword(
            @RequestParam("username")String username
    ){
        userService.remindPassword(username);
        return "redirect:/reminded?error=" + userService.isUserNotRegistered(username);
    }

}
