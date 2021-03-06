package com.damiannguyen.GW2GuildHelper.modules.guild.log;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.items.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "guild_log")
@Table(indexes = {
        @Index(columnList = "guild_id"),
        @Index(columnList = "operation")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    private Long id;
    @JoinColumn(name = "guild_id")
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

    public String getCoinsConverted(){
        return convertGold(getCoins());
    }

    private String convertGold(int gold){

        StringBuilder stringBuilder = new StringBuilder();
        int tempCoins = coins;
        if (coins  > 0) {
            if (tempCoins / 10000 > 0) {
                stringBuilder.append(tempCoins / 10000).append("g");
                tempCoins = tempCoins % 10000;
            }

            if (tempCoins / 100 > 0) {
                stringBuilder.append(tempCoins / 100).append("s");
                tempCoins = tempCoins % 100;
            }

            if (tempCoins > 0) {
                stringBuilder.append(tempCoins).append("c");
            }
        }else{
            stringBuilder.append("-");
            if (tempCoins / -10000 > 0) {
                stringBuilder.append(tempCoins / -10000).append("g");
                tempCoins = tempCoins % 10000;
            }

            if (tempCoins / -100 > 0) {
                stringBuilder.append(tempCoins / -100).append("s");
                tempCoins = tempCoins % 100;
            }

            if (tempCoins <= 0) {
                stringBuilder.append(-tempCoins).append("c");
            }
        }

        return stringBuilder.toString();
    }
}
