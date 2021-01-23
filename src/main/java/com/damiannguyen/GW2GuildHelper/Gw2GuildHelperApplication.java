package com.damiannguyen.GW2GuildHelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableAsync
public class Gw2GuildHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(Gw2GuildHelperApplication.class, args);
	}

}
