package com.damiannguyen.GW2GuildHelper.modules.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/app/welcome")
    public String getWelcome(){
        return "app/welcome";
    }
}
