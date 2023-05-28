package com.example.practicespring.controller;

import com.example.practicespring.config.BaseResponse;
import com.example.practicespring.config.BaseResponseStatus;
import com.example.practicespring.dto.request.PatchUserReq;
import com.example.practicespring.dto.request.PostUsersReq;
import com.example.practicespring.dto.request.PutUserImgReq;
import com.example.practicespring.dto.response.GetUserRes;
import com.example.practicespring.dto.response.PostUsersRes;
import com.example.practicespring.entity.Users;
import com.example.practicespring.repository.UsersRepository;
import com.example.practicespring.service.UsersService;
import com.example.practicespring.config.BaseException;
import com.example.practicespring.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.practicespring.utils.ValidationRegex.isRegexNumber;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UsersController {
    private final UsersService usersService;
    private final UsersRepository userRepository;

    private final JwtService jwtService;

    //회원가입
    @PostMapping("/join")
    public BaseResponse<PostUsersRes> joinUser(@RequestBody PostUsersReq postUsersReq){
        if(!isRegexNumber(postUsersReq.getPhone_number())) return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PHONE_NUMBER);
        try{
            return new BaseResponse<>(usersService.joinUser(postUsersReq));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    //회원 정보
    @GetMapping("/read/{nickName}")
    public BaseResponse<List<GetUserRes>> getUsers(@PathVariable(value = "nickName",required = false) String nickName ){
        try{
            if(nickName == null){
                return new BaseResponse<>(usersService.getUsers());
            }
            return new BaseResponse<>(usersService.getUsersByNickname(nickName));
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
    /* 로그인 구현
    @PostMapping("/log-in")
    public BaseResponse<PostLoginRes> loginUser(@RequestParam PostLoginReq postLoginReq){
        try{
            if(!isRegexNumber(postLoginReq.getPhone_number())) return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PHONE_NUMBER);
            return new BaseResponse<>(usersService.login(postLoginReq));
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
    */
    //닉네임 수정
    @PatchMapping("/nicknameupdate")
    public BaseResponse<String> modifyUserInfo(@RequestParam String phone_number,@RequestParam String nickname){
        Users users = userRepository.findUserByPhone_number(phone_number);
        PatchUserReq patchUserReq = new PatchUserReq(users.getId(),nickname);
        usersService.modifyUserName(patchUserReq);
        String result = "닉네임이 수정되었습니다.";
        return new BaseResponse<>(result);
    }
    @PutMapping("/imageupdate")
    public BaseResponse<String> modifyUserImg(@RequestParam String phone_number, @RequestParam String img_url){
        Users users = userRepository.findUserByPhone_number(phone_number);
        PutUserImgReq putUserImgReq = new PutUserImgReq(users.getId(),img_url);
        usersService.putUserImg(putUserImgReq);
        String result = "이미지가 등록(변경)되었습니다";
        return new BaseResponse<>(result);
    }
}
