package com.damiannguyen.GW2GuildHelper.modules.treasury;

import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.log.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreasuryService {
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private LogRepository logRepository;

    public List<Log> getTreasuryLog(){
        Guild guild = userHelper.getUser().getGuild();
        return logRepository.findAllByTypeAndGuild("treasury", guild);
    }
}
