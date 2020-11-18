package com.damiannguyen.GW2GuildHelper.modules.mappers;

import com.damiannguyen.GW2GuildHelper.modules.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.log.LogPojo;

import java.time.LocalDate;

public class LogMapper {
    public static Log map(LogPojo logPojo){
        return new Log(
                logPojo.getId(),
                logPojo.getTime(),
                logPojo.getUser(),
                logPojo.getType(),
                logPojo.getInvitedBy(),
                logPojo.getKickedBy(),
                logPojo.getChangedBy(),
                logPojo.getOldRank(),
                logPojo.getNewRank(),
                logPojo.getItemId(),
                logPojo.getCount(),
                logPojo.getOperation(),
                logPojo.getCoins(),
                logPojo.getMotd(),
                logPojo.getAction(),
                logPojo.getUpgradeId(),
                logPojo.getRecipeId());
//        private LocalDate time;
//        private String user;
//        private String type;
//        private String invitedBy;
//        private String kickedBy;
//        private String changedBy;
//        private String oldRank;
//        private String newRank;
//        private int itemId;
//        private int count;
//        private String operation;
//        private int coins;
//        private String motd;
//        private String action;
//        private int upgradeId;
//        private int recipeId;
    }
}
