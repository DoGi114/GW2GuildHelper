package com.damiannguyen.GW2GuildHelper.modules.roaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoasterController {
    @Autowired
    RoasterService roasterService;

    @GetMapping("/app/roaster/basic")
    public String getRoaster(Model model){
        model.addAttribute("roaster", roasterService.getRoaster());
        return "/app/roaster/basic";
    }
}
