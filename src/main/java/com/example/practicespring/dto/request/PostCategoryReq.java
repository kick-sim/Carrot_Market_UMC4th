package com.example.practicespring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCategoryReq {
    private String CateName;

    public PostCategoryReq() {
    }
}
