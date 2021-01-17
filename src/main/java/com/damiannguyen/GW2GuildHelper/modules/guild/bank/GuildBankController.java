package com.damiannguyen.GW2GuildHelper.modules.guild.bank;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class GuildBankController {
    private final GuildBankService guildBankService;

    @GetMapping("app/guildbank/log")
    public String getGuildBankLog(Model model){
        model.addAttribute("log", guildBankService.getGuildBankLog());
        return "app/guildbank/log";
    }
}
