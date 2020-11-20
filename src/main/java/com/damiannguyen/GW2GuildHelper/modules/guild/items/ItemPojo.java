package com.damiannguyen.GW2GuildHelper.modules.guild.items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemPojo {
    private Long id;
    private String name;
    private String icon;
}
