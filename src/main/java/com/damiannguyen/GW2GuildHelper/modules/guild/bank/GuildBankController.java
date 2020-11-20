package com.damiannguyen.GW2GuildHelper.modules.guild.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuildBankController {
    @Autowired
    GuildBankService guildBankService;

    @GetMapping("app/guildbank/log")
    public String getGuildBankLog(Model model){
        model.addAttribute("log", guildBankService.getGuildBankLog());
        return "/app/guildbank/log";
    }
}
