package com.damiannguyen.GW2GuildHelper.modules.mappers;

import com.damiannguyen.GW2GuildHelper.core.security.UserHelper;
import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.log.LogPojo;
import com.damiannguyen.GW2GuildHelper.modules.users.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class LogMapper {

    public static Log map(LogPojo logPojo, Guild guild){
        return new Log(
                logPojo.getId(),
                guild,
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
    }
}
