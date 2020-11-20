package com.damiannguyen.GW2GuildHelper.modules.log;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.items.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity(name = "guild_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Guild guild;
    private LocalDateTime time;
    private String user;
    private String type;
    private String invitedBy;
    private String kickedBy;
    private String changedBy;
    private String oldRank;
    private String newRank;
    @OneToOne(cascade = CascadeType.ALL)
    private Item item;
    private int count;
    private String operation;
    private int coins;
    private String motd;
    private String action;
    private int upgradeId;
    private int recipeId;
}
