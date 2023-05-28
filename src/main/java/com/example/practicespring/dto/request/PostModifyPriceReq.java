package com.example.practicespring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostModifyPriceReq {
    long productId;
    long userId;
    int price;
}
