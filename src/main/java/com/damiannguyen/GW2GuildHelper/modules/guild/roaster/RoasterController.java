package com.damiannguyen.GW2GuildHelper.modules.guild.roaster;

import com.damiannguyen.GW2GuildHelper.modules.users.User;
import com.damiannguyen.GW2GuildHelper.modules.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class RoasterController {
    private final RoasterService roasterService;
    private final UserRepository userRepository;

    @GetMapping("/app/roaster/basic")
    public String getRoaster(Model model){
        model.addAttribute("roaster", roasterService.getRoaster());
        return "app/roaster/basic";
    }

    @GetMapping("app/roaster/log")
    public String getRoasterLog(Model model){
        model.addAttribute("log", roasterService.getRoasterLog());
        return "app/roaster/log";
    }

    @GetMapping("app/roaster/details/{name}")
    public String getRoasterDetails(
            @PathVariable(value="name") String name,
            Model model
    ){
        model.addAttribute("user", name);
        model.addAttribute("donationList", roasterService.getAccountDonations(name));
        model.addAttribute("withdrawList", roasterService.getAccountWithdraws(name));
        return "app/roaster/details";
    }
}
