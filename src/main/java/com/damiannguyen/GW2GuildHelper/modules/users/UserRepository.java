package com.damiannguyen.GW2GuildHelper.modules.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String mail);
}
