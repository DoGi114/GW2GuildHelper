package com.damiannguyen.GW2GuildHelper.modules.guild.treasury;

import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreasuryService {
    private final UserHelper userHelper;
    private final LogRepository logRepository;

    public List<Log> getTreasuryLog(){
        Guild guild = userHelper.getUser().getGuild();
        return logRepository.findAllByTypeAndGuild("treasury", guild)
                .stream()
                .sorted((o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                .collect(Collectors.toList());
    }
}
