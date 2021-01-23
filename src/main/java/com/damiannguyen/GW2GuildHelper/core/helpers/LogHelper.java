package com.damiannguyen.GW2GuildHelper.core.helpers;

import com.damiannguyen.GW2GuildHelper.modules.guild.log.Log;

import java.util.List;

public class LogHelper {

    public static List<Log> AddUpDuplicatedLogs(List<Log> resultList, List<Log> allByOperationAndGuildAndUser) {
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
        }

        return resultList;
    }
}
