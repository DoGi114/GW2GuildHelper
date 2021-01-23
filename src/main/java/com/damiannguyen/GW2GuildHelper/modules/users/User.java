package com.damiannguyen.GW2GuildHelper.modules.users;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.users.role.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.id.UUIDGenerator;

import javax.persistence.*;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class User {
    //TODO: Add confirming user by admin!
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    private Guild guild;
    @OneToOne(cascade = CascadeType.ALL)
    private Role role;
    @Transient
    private String passwordConfirm;
    private String uuid;

    public User(String username, String email, String password, Guild guild, Role role) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.guild = guild;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                '}';
    }
}
