package com.damiannguyen.GW2GuildHelper.modules.guild.bank;

import com.damiannguyen.GW2GuildHelper.core.StashOperation;
import com.damiannguyen.GW2GuildHelper.core.helpers.LogHelper;
import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.items.ItemRepository;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class GuildBankService {
    private final UserHelper userHelper;
    private final LogRepository logRepository;
    private final ItemRepository itemRepository;

    public List<Log> getGuildBankLog(){
        Guild guild = userHelper.getUser().getGuild();
        List<Log> deposit = logRepository.findAllByOperationAndGuild(StashOperation.DEPOSIT.toString(), guild);
        List<Log> withdraw = logRepository.findAllByOperationAndGuild(StashOperation.WITHDRAW.toString(), guild);
        List<Log> move = logRepository.findAllByOperationAndGuild(StashOperation.MOVE.toString(), guild);
        return Stream.concat(Stream.concat(deposit.stream(), withdraw.stream()), move.stream())
                        .sorted((o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                .collect(Collectors.toList());
    }

    public Object getGuildBankGoldSummary() {
        Guild guild = userHelper.getUser().getGuild();
        List<Log> deposit = new ArrayList<>();
        List<Log> withdraw = new ArrayList<>();

        List<Log> allByOperationAndItemAndGuildDeposited = logRepository.findAllByOperationAndItemAndGuild(
                StashOperation.DEPOSIT.toString(),
                itemRepository.getOne(0L),
                guild);

        SumCoinsForUserInList(deposit, allByOperationAndItemAndGuildDeposited);

        List<Log> allByOperationAndItemAndGuildWithdraw = logRepository.findAllByOperationAndItemAndGuild(
                StashOperation.WITHDRAW.toString(),
                itemRepository.getOne(0L),
                guild);

        SumCoinsForUserInList(withdraw, allByOperationAndItemAndGuildWithdraw);

        for (Log depositLog : allByOperationAndItemAndGuildDeposited) {
                Log withdrawLog = allByOperationAndItemAndGuildWithdraw.stream().filter( withdrawLogPredicate ->
                        withdrawLogPredicate.getUser().equals(depositLog.getUser())).findFirst().orElse(new Log());
                depositLog.setCoins(depositLog.getCoins() - withdrawLog.getCoins());
        }

        return deposit;
    }

    private void SumCoinsForUserInList(List<Log> summary, List<Log> allByOperationAndItemAndGuildWithdraw) {
        for (Log log : allByOperationAndItemAndGuildWithdraw) {
            if(summary.stream().anyMatch(summaryLog -> summaryLog.getUser().equals(log.getUser()) )){
                Log updateLog = summary.stream().filter( summaryUpdate -> summaryUpdate.getUser().equals(log.getUser())).findFirst().orElseThrow();
                updateLog.setCoins(updateLog.getCoins() + log.getCoins());
            }else{
                summary.add(log);
            }
        }
    }

    public List<Log> getAccountGoldDonations(String username){
        Guild guild = userHelper.getUser().getGuild();
        return logRepository.findAllByOperationAndItemAndGuildAndUser(StashOperation.DEPOSIT.toString(), itemRepository.getOne(0L), guild, username);
    }

    public List<Log> getAccountGoldWithdraws(String username){
        Guild guild = userHelper.getUser().getGuild();
        return logRepository.findAllByOperationAndItemAndGuildAndUser(StashOperation.WITHDRAW.toString(), itemRepository.getOne(0L), guild, username);
    }

    public List<Log> getAccountGoldSummary(String username){
        return Stream.concat(getAccountGoldDonations(username).stream(), getAccountGoldWithdraws(username)
                .stream())
                .sorted( (o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                .collect(Collectors.toList());
    }
}
