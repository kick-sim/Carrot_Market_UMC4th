package com.example.practicespring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostProductReq {
    private String sellerEmail;
    private String category;
    private String zipCode;
    private String title;
    private int price;
    private String content;
}




