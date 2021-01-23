package com.damiannguyen.GW2GuildHelper.modules.welcome;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping({"/app/home", "/"})
    public String getWelcome(Model model){
        return "app/home";
    }

    @GetMapping({"/app/refresh"})
    public void getRefresh(Model model){
        homeService.loadLog();
        //TODO: Dont redirect!!!
//        return "";
    }

}
