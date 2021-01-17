package com.damiannguyen.GW2GuildHelper.modules.guild.bank;

import com.damiannguyen.GW2GuildHelper.core.StashOperation;
import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class GuildBankService {
    private final UserHelper userHelper;
    private final LogRepository logRepository;

    public List<Log> getGuildBankLog(){
        Guild guild = userHelper.getUser().getGuild();
        List<Log> deposit = logRepository.findAllByOperationAndGuild(StashOperation.DEPOSIT.toString(), guild);
        List<Log> withdraw = logRepository.findAllByOperationAndGuild(StashOperation.WITHDRAW.toString(), guild);
        List<Log> move = logRepository.findAllByOperationAndGuild(StashOperation.MOVE.toString(), guild);
        return Stream.concat(Stream.concat(deposit.stream(), withdraw.stream()), move.stream())
                        .sorted((o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                .collect(Collectors.toList());
    }
}
