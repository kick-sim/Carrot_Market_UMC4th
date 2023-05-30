package com.example.practicespring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String phone_number;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private float manner_rate;
    @Column(nullable = false)
    private int activated;
    @Column(nullable = false)
    private LocalDateTime created_at;
    @Column(nullable = false)
    private LocalDateTime updated_at;

    public Users createUsers(String phone_number, String nickname) {
        this.phone_number = phone_number;
        this.nickname = nickname;
        this.manner_rate = 36.5F;
        this.activated = 1;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
        return this;
    }

    public void updateNickName(String nickName) {
        this.nickname = nickName;
        this.updated_at = LocalDateTime.now();
    }
}
