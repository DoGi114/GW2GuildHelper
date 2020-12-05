package com.damiannguyen.GW2GuildHelper.modules.welcome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @Autowired
    private WelcomeService welcomeService;

    @GetMapping({"/app/welcome", "/"})
    public String getWelcome(Model model){
        //TODO: Move to another module
        String code = welcomeService.loadLog();
        model.addAttribute("code", code);
        return "app/welcome";
    }


}
