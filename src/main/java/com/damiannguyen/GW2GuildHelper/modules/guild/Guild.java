package com.damiannguyen.GW2GuildHelper.modules.guild;

import javax.persistence.*;

@Entity(name = "guild")
public class Guild {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "guild_id")
    private String guildId;
    @Column(name = "leader_api_key")
    private String leaderApiKey;

    public Guild(){

    }

    public Guild(String name) {
        this.name = name;
    }

    public Guild(Long id, String name, String guildId, String leaderApiKey) {
        this.id = id;
        this.name = name;
        this.guildId = guildId;
        this.leaderApiKey = leaderApiKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getLeaderApiKey() {
        return leaderApiKey;
    }

    public void setLeaderApiKey(String leaderApiKey) {
        this.leaderApiKey = leaderApiKey;
    }
}
