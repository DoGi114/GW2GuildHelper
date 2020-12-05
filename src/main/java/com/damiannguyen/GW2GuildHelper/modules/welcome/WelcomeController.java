package com.damiannguyen.GW2GuildHelper.modules.welcome;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WelcomeController {
    private final WelcomeService welcomeService;

    @GetMapping({"/app/welcome", "/"})
    public String getWelcome(Model model){
        //TODO: Move to another module
//        String code = welcomeService.loadLog();
//        model.addAttribute("code", code);
        return "app/welcome";
    }


}
