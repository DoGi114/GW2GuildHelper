package com.damiannguyen.GW2GuildHelper.modules.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    UserRepository repository;

    @GetMapping("/register")
    public String register(){
        LOGGER.info("register");

        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password_confirm") String passwordConfirm
    ) {

        //TODO: Check passwords

        repository.save(new User(email, encoder.encode(password)));


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

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "/app/welcome";
    }

}
