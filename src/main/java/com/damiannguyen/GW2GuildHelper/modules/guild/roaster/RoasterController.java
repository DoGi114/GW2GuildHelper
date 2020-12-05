package com.damiannguyen.GW2GuildHelper.modules.guild.roaster;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RoasterController {
    private final RoasterService roasterService;

//    @Autowired
//    public RoasterController(RoasterService roasterService) {
//        this.roasterService = roasterService;
//    }

    @GetMapping("/app/roaster/basic")
    public String getRoaster(Model model){
        model.addAttribute("roaster", roasterService.getRoaster());
        return "/app/roaster/basic";
    }

    @GetMapping("app/roaster/log")
    public String getRoasterLog(Model model){
        model.addAttribute("log", roasterService.getRoasterLog());
        return "/app/roaster/log";
    }
}
