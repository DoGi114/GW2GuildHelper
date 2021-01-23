package com.damiannguyen.GW2GuildHelper.modules.users;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByGuild(Guild guild);
    boolean existsByUsername(String username);
    User findByEmail(String email);
    User findByUuid(String uuid);
}
