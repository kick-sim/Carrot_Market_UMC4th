package com.example.practicespring.dto.request;

import com.example.practicespring.entity.Area;
import com.example.practicespring.entity.Product_Category;
import com.example.practicespring.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostProductReq {
    private String sellerNumber;
    private String category;
    private String zipCode;
    private String title;
    private int price;
    private String content;
}




