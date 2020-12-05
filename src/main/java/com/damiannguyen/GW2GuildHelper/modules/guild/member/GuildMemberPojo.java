package com.damiannguyen.GW2GuildHelper.modules.guild.member;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class GuildMemberPojo implements Serializable {
    private String name;
    private LocalDateTime joined;
    private String rank;
}
