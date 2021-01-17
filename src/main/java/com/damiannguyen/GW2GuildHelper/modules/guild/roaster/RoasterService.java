package com.damiannguyen.GW2GuildHelper.modules.guild.roaster;

import com.damiannguyen.GW2GuildHelper.core.StashOperation;
import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.items.Item;
import com.damiannguyen.GW2GuildHelper.modules.guild.items.ItemRepository;
import com.damiannguyen.GW2GuildHelper.modules.guild.member.GuildMemberPojo;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogRepository;
import com.damiannguyen.GW2GuildHelper.modules.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RoasterService {
    private final UserHelper userHelper;
    private final LogRepository logRepository;
    private final ItemRepository itemRepository;

    public List<GuildMemberPojo> getRoaster(){
        User user = userHelper.getUser();
        String api = "https://api.guildwars2.com/v2/guild/" + user.getGuild().getGuildId() + "/members?access_token=" + user.getGuild().getLeaderApiKey() ;
        RestTemplate restTemplate = new RestTemplate();

        //TODO:Invalid access token

        ResponseEntity<GuildMemberPojo[]> response =
                restTemplate.getForEntity(
                        api,
                        GuildMemberPojo[].class);

        return Arrays.stream(response.getBody())
//                .sorted((o1, o2) -> o2.getJoined().compareTo(o1.getJoined()))
                .collect(Collectors.toList());
    }

    public List<Log> getRoasterLog(){
        Guild guild = userHelper.getUser().getGuild();
        List<Log> invited = logRepository.findAllByTypeAndGuild("invited", guild);
        List<Log> joined = logRepository.findAllByTypeAndGuild("joined", guild);
        List<Log> kicked = logRepository.findAllByTypeAndGuild("kick", guild);

        return Stream.concat(Stream.concat(kicked.stream(), joined.stream()), invited.stream())
                .sorted((o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                .collect(Collectors.toList());
    }

    public List<Log> getAccountDonations(String username){
        List<Log> resultList = new ArrayList<>();
        Guild guild = userHelper.getUser().getGuild();
        List<Log> allByOperationAndGuildAndUser = logRepository.findAllByOperationAndGuildAndUser(StashOperation.DEPOSIT.toString(), guild, username);
        //TODO:Enum
        List<Log> allByTypeAndGuildAndUser = logRepository.findAllByTypeAndGuildAndUser("Treasury", guild, username);
        List<Log> allByUser = Stream.concat(allByTypeAndGuildAndUser.stream(), allByOperationAndGuildAndUser.stream()).collect(Collectors.toList());
        return AddUpDuplicatedLogs(resultList, allByUser);
    }

    public List<Log> getAccountWithdraws(String username){
        List<Log> resultList = new ArrayList<>();
        Guild guild = userHelper.getUser().getGuild();
        List<Log> allByOperationAndGuildAndUser = logRepository.findAllByOperationAndGuildAndUser(StashOperation.WITHDRAW.toString(), guild, username);
        return AddUpDuplicatedLogs(resultList, allByOperationAndGuildAndUser);
    }

    private List<Log> AddUpDuplicatedLogs(List<Log> resultList, List<Log> allByOperationAndGuildAndUser) {
        for(Log log : allByOperationAndGuildAndUser){
            Log updateLog;

            if(log.getItem().getId() != 0){
                if(resultList.stream().anyMatch(resultLog -> (resultLog.getItem().equals(log.getItem())))){
                    updateLog = resultList.stream().filter(resultLog -> resultLog.getItem().equals(log.getItem())).findFirst().orElseThrow();
                    updateLog.setCount(updateLog.getCount() + log.getCount());
                }else{
                    resultList.add(log);
                }
            }else{
                if(resultList.stream().anyMatch(resultLog -> (resultLog.getItem().getId().equals(0L)))){
                    updateLog = resultList.stream().filter(resultLog -> resultLog.getItem().getId().equals(log.getItem().getId())).findFirst().orElseThrow();
                    updateLog.setCoins(updateLog.getCoins() + log.getCoins());
                    System.out.println(updateLog.getCoins());
                }else{
                    resultList.add(log);
                }
            }

//            if(resultList.stream().anyMatch(resultLog -> (resultLog.getItem().equals(log.getItem())))){
//                Log updateLog = resultList.stream().filter(resultLog -> resultLog.getItem().equals(log.getItem())).findFirst().orElseThrow();
//                updateLog.setCount(updateLog.getCount() + log.getCount());
//            }else if(( log.getCoins() > 0 )){
//                Log updateLog;
//                if(!resultList.stream().anyMatch(resultLog -> resultLog.getId().equals(0) )){
//                    updateLog = new Log();
//                    updateLog.setId(0L);
//                    resultList.add(updateLog);
//                }else {
//                    updateLog = resultList.stream().filter(resultLog -> resultLog.getCoins() > 0).findFirst().orElseThrow();
//                }
//                updateLog.setCoins(updateLog.getCoins() + log.getCoins());
//                System.out.println(updateLog.getCoins());
//            }else{
//                resultList.add(log);
//            }
        }

        return resultList;
    }
}
