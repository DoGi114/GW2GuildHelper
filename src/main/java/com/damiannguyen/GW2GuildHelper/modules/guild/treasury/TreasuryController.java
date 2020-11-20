package com.damiannguyen.GW2GuildHelper.modules.guild.treasury;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TreasuryController {
    @Autowired
    private TreasuryService treasuryService;

    @GetMapping("app/treasury/log")
    public String getTreasuryLog(Model model){
        model.addAttribute("log", treasuryService.getTreasuryLog());
        return "app/treasury/log";
    }
}
