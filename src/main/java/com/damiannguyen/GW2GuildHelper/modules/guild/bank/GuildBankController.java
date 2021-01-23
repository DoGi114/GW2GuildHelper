package com.damiannguyen.GW2GuildHelper.modules.guild.bank;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("app/guildbank/gold-summary")
    public String getGuildBankGoldSummary(Model model){
        model.addAttribute("summary", guildBankService.getGuildBankGoldSummary());
        return "app/guildbank/gold-summary";
    }

    @GetMapping("app/guildbank/gold-summary/{name}")
    public String getRoasterDetails(
            @PathVariable(value="name") String name,
            Model model
    ){
        model.addAttribute("user", name);
        model.addAttribute("summary", guildBankService.getAccountGoldSummary(name));
        return "app/guildbank/gold-details";
    }
}
