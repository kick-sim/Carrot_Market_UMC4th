package com.example.practicespring.dto.response;

import com.example.practicespring.entity.Area;
import com.example.practicespring.entity.Product_Category;
import com.example.practicespring.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PostProductRes {
    private String seller;
    private String category;
    private String zipCode;
    private String title;
    private int price;
    private String content;
}

