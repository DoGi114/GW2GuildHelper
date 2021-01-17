package com.damiannguyen.GW2GuildHelper.modules.guild.log;

import com.damiannguyen.GW2GuildHelper.core.StashOperation;
import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAllByTypeAndGuild(String type, Guild guild);
    List<Log> findAllByOperationAndGuild(String operation, Guild guild);
    //TODO:Check
//    List<Log> findAllByOperationInAndGuild(Set<String> operations, Guild guild);
    List<Log> findAllByOperationAndGuildAndUser(String operation, Guild guild, String user);
    List<Log> findAllByTypeAndGuildAndUser(String type, Guild guild, String user);
}
