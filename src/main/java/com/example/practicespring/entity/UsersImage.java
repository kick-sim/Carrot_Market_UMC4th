package com.example.practicespring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UsersImage implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    Users user;
    @Column(name="image_url")
    private String image_url;
    public void putImg(String image_url){
        this.image_url = image_url;
        user.setUpdated_at(LocalDateTime.now());
    }
    public UsersImage createImage(Users user){
        this.image_url = "NONE";
        this.user = user;
        return this;
    }
}
