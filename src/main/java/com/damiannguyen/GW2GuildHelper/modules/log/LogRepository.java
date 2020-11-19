package com.damiannguyen.GW2GuildHelper.modules.log;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAllByTypeAndGuild(String type, Guild guild);
}
