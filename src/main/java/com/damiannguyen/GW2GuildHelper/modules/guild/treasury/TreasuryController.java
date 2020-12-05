package com.damiannguyen.GW2GuildHelper.modules.guild.treasury;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TreasuryController {
    private final TreasuryService treasuryService;

    @GetMapping("app/treasury/log")
    public String getTreasuryLog(Model model){
        model.addAttribute("log", treasuryService.getTreasuryLog());
        return "app/treasury/log";
    }
}
