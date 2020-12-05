package com.damiannguyen.GW2GuildHelper.modules.guild.bank;

import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GuildBankService {
    private final UserHelper userHelper;
    private final LogRepository logRepository;

    public List<Log> getGuildBankLog(){
        Guild guild = userHelper.getUser().getGuild();
        List<Log> deposit = logRepository.findAllByOperationAndGuild("deposit", guild);
        List<Log> withdraw = logRepository.findAllByOperationAndGuild("withdraw", guild);
        List<Log> move = logRepository.findAllByOperationAndGuild("move", guild);
        return Stream.concat(Stream.concat(deposit.stream(), withdraw.stream()), move.stream())
                .sorted((o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                .collect(Collectors.toList());
    }
}
