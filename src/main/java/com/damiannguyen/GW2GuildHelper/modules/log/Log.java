package com.damiannguyen.GW2GuildHelper.modules.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "guild_log")
@Data
@NoArgsConstructor
public class Log {
    @Id
    private Long id;
    private LocalDateTime time;
    private String user;
    private String type;
    private String invitedBy;
    private String kickedBy;
    private String changedBy;
    private String oldRank;
    private String newRank;
    private int itemId;
    private int count;
    private String operation;
    private int coins;
    private String motd;
    private String action;
    private int upgradeId;
    private int recipeId;

    public Log(Long id, LocalDateTime time, String user, String type, String invitedBy, String kickedBy, String changedBy, String oldRank, String newRank, int itemId, int count, String operation, int coins, String motd, String action, int upgradeId, int recipeId) {
        this.id = id;
        this.time = time;
        this.user = user;
        this.type = type;
        this.invitedBy = invitedBy;
        this.kickedBy = kickedBy;
        this.changedBy = changedBy;
        this.oldRank = oldRank;
        this.newRank = newRank;
        this.itemId = itemId;
        this.count = count;
        this.operation = operation;
        this.coins = coins;
        this.motd = motd;
        this.action = action;
        this.upgradeId = upgradeId;
        this.recipeId = recipeId;
    }
}
