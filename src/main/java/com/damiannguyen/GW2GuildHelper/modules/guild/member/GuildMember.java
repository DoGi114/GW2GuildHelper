package com.damiannguyen.GW2GuildHelper.modules.guild.member;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class GuildMember implements Serializable {
    private String name;
    private LocalDate joined;
    private String rank;

    @Override
    public String toString() {
        return "GuildMember{" +
                "tag='" + name + '\'' +
                ", joined=" + joined +
                ", rank='" + rank + '\'' +
                '}';
    }


}
