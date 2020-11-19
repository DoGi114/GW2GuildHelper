package com.damiannguyen.GW2GuildHelper.modules.settings;

import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.users.User;
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
    @Autowired
    private UserHelper userHelper;

    @GetMapping("/app/settings")
    public String getSettings(Model model){
        User user = userHelper.getUser();

        if(user != null){
            model.addAttribute("role", user.getRole().getName());
        }

        return "/app/settings/settings";
    }

    @GetMapping("/app/settings/api")
    public String getApiSettings(Model model){
        User user = userHelper.getUser();

        if(user != null){
            model.addAttribute("personal_api", settingsService.getLeaderApiKey(user.getGuild()));
            model.addAttribute("guild_api", settingsService.getGuildId(user.getGuild()));
        }
        return "/app/settings/api";
    }

    @PostMapping("/app/settings/api")
    public String setSettings(
        @RequestParam("personal_api") String personalApi,
        @RequestParam("guild_api") String guildApi,
        RedirectAttributes attributes
    ){

        User user = userHelper.getUser();

        if(user != null){

            settingsService.setApi(personalApi, guildApi, user.getGuild());

            attributes.addFlashAttribute(
                    "message",
                    "Changed credentials"
            );
        }else{
            attributes.addFlashAttribute(
                "error",
                "Something went wrong :("
        );
        }
        return "redirect:/app/settings/api";
    }
}
