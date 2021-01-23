package com.damiannguyen.GW2GuildHelper.modules.settings;

import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.users.User;
import com.damiannguyen.GW2GuildHelper.modules.users.UserRepository;
import com.damiannguyen.GW2GuildHelper.modules.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder encoder;
    private final SettingsService settingsService;
    private final UserHelper userHelper;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/app/settings")
    public String getSettings(Model model){
        User user = userHelper.getUser();

        if(user != null){
            model.addAttribute("role", user.getRole().getName());
        }

        assert user != null;
        model.addAttribute("guild_users", settingsService.getUsersInGuild(user.getGuild()));

        return "app/settings/settings";
    }

    @PostMapping("/app/settings")
    public String setSettings(
            @RequestParam("admin_checkbox") List<String> adminUsers
    ){
        User user = userHelper.getUser();

        settingsService.setGuildAdmin(user.getGuild(), adminUsers);
        return "redirect:/app/settings/";
    }

    @GetMapping("/app/settings/api")
    public String getApiSettings(Model model){
        User user = userHelper.getUser();

        if(user != null){
            model.addAttribute("personal_api", settingsService.getLeaderApiKey(user.getGuild()));
            model.addAttribute("guild_api", settingsService.getGuildId(user.getGuild()));
        }
        return "app/settings/api";
    }

    @PostMapping("/app/settings/api")
    public String setApiSettings(
        @RequestParam("personal_api") String personalApi,
        @RequestParam("guild_api") String guildApi,
        RedirectAttributes attributes
    ){
        //TODO: Add option to write guild name instead of ID
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

    @PostMapping("/app/settings/change-password")
    public String changePassword(
        @RequestParam("current-password") String currentPassword,
        @RequestParam("new-password") String newPassword,
        @RequestParam("confirm-new-password") String confirmNewPassword,
        RedirectAttributes redirectAttributes
    ){
        if(userService.isPasswordSame(userHelper.getUser().getPassword(), encoder.encode(currentPassword))){
            User user = userHelper.getUser();
            if(newPassword.equals(confirmNewPassword)) {
                user.setPassword(newPassword);
                user.setPasswordConfirm(confirmNewPassword);
                userRepository.save(user);
            }else{
                redirectAttributes.addFlashAttribute("error", "New password doesnt match confirm password");
            }
        }else{
            redirectAttributes.addFlashAttribute("error", "Current password is incorrect");
        }
        return "redirect:/app/settings";
    }
}
