package com.damiannguyen.GW2GuildHelper.modules.guild;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildRepository extends JpaRepository<Guild, Long>{
    Guild findByName(String name);
}
