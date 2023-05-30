package com.example.practicespring.controller;

import com.example.practicespring.config.BaseException;
import com.example.practicespring.config.BaseResponse;
import com.example.practicespring.config.BaseResponseStatus;
import com.example.practicespring.dto.request.PostAreaReq;
import com.example.practicespring.dto.response.PostAreaRes;
import com.example.practicespring.repository.AreaRepository;
import com.example.practicespring.repository.UsersRepository;
import com.example.practicespring.service.AreaService;
import com.example.practicespring.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/area")
public class AreaController {
    private final AreaService areaService;
    private final AreaRepository areaRepository;

    //지역등록
    @PostMapping("/enroll")
    public BaseResponse<PostAreaRes> enrollArea(@RequestBody PostAreaReq postAreaReq) {
        try {
            if (postAreaReq.getAddress() == null || postAreaReq.getZip_code() == null) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_REQ);
            }
            return new BaseResponse<>(areaService.enrollArea(postAreaReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
