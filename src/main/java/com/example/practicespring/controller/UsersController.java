package com.example.practicespring.controller;

import com.example.practicespring.config.BaseResponse;
import com.example.practicespring.config.BaseResponseStatus;
import com.example.practicespring.dto.request.PatchUserReq;
import com.example.practicespring.dto.request.PostLoginReq;
import com.example.practicespring.dto.request.PostUsersReq;
import com.example.practicespring.dto.request.PutUserImgReq;
import com.example.practicespring.dto.response.GetUserRes;
import com.example.practicespring.dto.response.PostLoginRes;
import com.example.practicespring.dto.response.PostUsersRes;
import com.example.practicespring.entity.Users;
import com.example.practicespring.repository.UsersRepository;
import com.example.practicespring.service.UsersService;
import com.example.practicespring.config.BaseException;
import com.example.practicespring.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.practicespring.utils.ValidationRegex.isRegexEmail;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UsersController {
    private final UsersService usersService;
    private final UsersRepository userRepository;

    private final JwtService jwtService;

    //회원가입
    @PostMapping("/join")
    public BaseResponse<PostUsersRes> joinUser(@RequestBody PostUsersReq postUsersReq) {
        if (!isRegexEmail(postUsersReq.getEmail()))
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
        try {
            return new BaseResponse<>(usersService.joinUser(postUsersReq));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/login")
    public BaseResponse<PostLoginRes> loginUser(@RequestBody PostLoginReq postLoginReq) {
        try {
            if (!isRegexEmail(postLoginReq.getEmail()))
                return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
            return new BaseResponse<>(usersService.login(postLoginReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //회원 정보 조회
    @GetMapping({"/read/{nickName}", "/read"})
    public BaseResponse<List<GetUserRes>> getUsers(@PathVariable(value = "nickName", required = false) String nickName) {
        try {
            if (nickName == null) {
                return new BaseResponse<>(usersService.getUsers());
            }
            return new BaseResponse<>(usersService.getUsersByNickname(nickName));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //닉네임 수정
    @PatchMapping("/nicknameupdate")
    public BaseResponse<String> modifyUserInfo(@RequestParam String email, @RequestParam String nickname) {
        try {
            Users user = userRepository.findUserByEmail(email);
            //jwt에서 idx 추출.
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(user.getId() != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchUserReq patchMemberReq = new PatchUserReq(user.getId(), nickname);
            usersService.modifyUserName(patchMemberReq);
            String result = "회원정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        }  catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //이미지 업데이트
    @PutMapping("/imageupdate")
    public BaseResponse<String> modifyUserImg(@RequestParam String email, @RequestParam String img_url) {
        try {
            Users user = userRepository.findUserByEmail(email);
            //jwt에서 idx 추출.
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(user.getId() != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            //같다면 유저이미지 변경
            PutUserImgReq putUserImgReq = new PutUserImgReq(user.getId(), img_url);
            usersService.putUserImg(putUserImgReq);
            String result = "이미지가 등록(변경)되었습니다";
            return new BaseResponse<>(result);
        }  catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}
