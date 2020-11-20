package com.damiannguyen.GW2GuildHelper.modules.guild.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogPojo {
    private Long id;// (number) - An ID to uniquely identify the log entry within the scope of the guild. Not globally unique.
    private LocalDateTime time; // (string) - ISO-8601 standard timestamp for when the log entry was created.
    private String user; // (string, optional) - The account name of the guild member who generated this log entry.
    private String type; // (string) - The type of log entry. Additional fields are given depending on the type. Possible values:
//    -joined; // - user has joined the guild
//    -invited - user has been invited to the guild. Additional fields include:
    @JsonProperty(value = "invited_by")
    private String invitedBy; //(string) - Account name of the guild member which invited the player.
//    kick - user has been kicked from the guild. Additional fields include:
    @JsonProperty(value = "kicked_by")
    private String kickedBy; // (string) - Account name of the guild member which kicked the player.
//    -rank_change - Rank for user has been changed. Additional fields include:
    @JsonProperty(value = "changed_by")
    private String changedBy; // (string) - Account name of the guild member which changed the player rank.
    @JsonProperty(value = "old_rank")
    private String oldRank; // (string) - Old rank name.
    @JsonProperty(value = "new_rank")
    private String newRank; // (string) - New rank name.
//    treasury - A guild member has deposited an item into the guild's treasury. Additional fields include:
    @JsonProperty(value = "item_id")
    private Long itemId; // id of deposited item
    private int count; // how many of item was deposited
//    stash - - A guild member has deposited/withdrawn an item into the guild stash. Additional fields include:
    private String operation; // (string) - Possible values:
//    deposit
//    withdraw
//    move
    private int coins; // (number) - How many coins (in copper) were deposited.
//    motd - A guild member has changed the guild's MOTD. Additional fields include:
    private String motd; // (string) - The new MOTD.
//    upgrade - A guild member has interacted with a guild upgrade. Additional fields include:
    private String action; // (string) - The type of interaction had. Possible values:
//    queued
//    cancelled
//    completed - Having this action will also generate a new count field indicating how many upgrades were added.
//    sped_up
    @JsonProperty(value = "upgrade_id")
    private int upgradeId; // (number) - The upgrade ID which was completed.
    @JsonProperty(value = "recipe_id")
    private int recipeId; // (number)(optional) - May be added if the upgrade was created through a scribe station by a scribe.
}
