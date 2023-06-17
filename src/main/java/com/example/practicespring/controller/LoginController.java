package com.example.practicespring.controller;

import com.example.practicespring.config.BaseException;
import com.example.practicespring.config.BaseResponse;
import com.example.practicespring.dto.response.PostLoginRes;
import com.example.practicespring.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class LoginController {

    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login/google")
    public BaseResponse<PostLoginRes> socialLogin(@RequestParam String code) throws BaseException {
        return new BaseResponse<>(loginService.socialLogin(code));
    }
}
