package com.damiannguyen.GW2GuildHelper.modules.log;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAllByType(String type);
}
