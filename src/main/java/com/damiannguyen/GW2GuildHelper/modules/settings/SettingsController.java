package com.damiannguyen.GW2GuildHelper.modules.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @GetMapping("/app/settings")
    public String getSettings(Model model){
        model.addAttribute("personal_api", settingsService.getPersonalApi());
        model.addAttribute("guild_api", settingsService.getGuildApi());
        return "/app/settings";
    }

    @PostMapping("/app/settings")
    public String setSettings(
        @RequestParam("personal_api") String personalApi,
        @RequestParam("guild_api") String guildApi,
        RedirectAttributes attributes
    ){
        settingsService.setApi(personalApi, guildApi);

        attributes.addFlashAttribute(
                "message",
                "Changed credentials"
        );
        return "redirect:/app/settings";
    }
}
