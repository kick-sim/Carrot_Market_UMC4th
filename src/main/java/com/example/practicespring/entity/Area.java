package com.example.practicespring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String zip_code;
    @Column(nullable = false)
    private LocalDateTime created_at;
    @Column(nullable = false)
    private LocalDateTime updated_at;

    public Area enrollArea(String address, String zip_code) {
        this.address = address;
        this.zip_code = zip_code;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
        return this;
    }
}