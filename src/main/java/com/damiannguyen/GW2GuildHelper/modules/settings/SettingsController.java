package com.damiannguyen.GW2GuildHelper.modules.settings;

import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.users.User;
import com.damiannguyen.GW2GuildHelper.modules.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;
    private final UserHelper userHelper;

    @GetMapping("/app/settings")
    public String getSettings(Model model){
        User user = userHelper.getUser();

        if(user != null){
            model.addAttribute("role", user.getRole().getName());
        }

        //TODO: Validation
        assert user != null;
        model.addAttribute("guild_users", settingsService.getUsersInGuild(user.getGuild()));
//        model.addAttribute("guild_admin", settingsService.getGuildAdmin(user.getGuild()));

        return "/app/settings/settings";
    }

    @PostMapping("/app/settings")
    public String setSettings(
            @RequestParam("admin_checkbox") List<String> adminUsers,
            RedirectAttributes attributes
    ){
        //TODO: Add possibility to have many admins
        User user = userHelper.getUser();

        settingsService.setGuildAdmin(user.getGuild(), adminUsers);
        //TODO: Some kind of reload of logedin user instead of relog
        return "redirect:/app/settings/";
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
    public String setApiSettings(
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
