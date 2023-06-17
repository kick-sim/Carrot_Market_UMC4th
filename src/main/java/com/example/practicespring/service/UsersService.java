package com.example.practicespring.service;

import com.example.practicespring.config.BaseException;
import com.example.practicespring.config.BaseResponseStatus;
import com.example.practicespring.dto.request.PatchUserReq;
import com.example.practicespring.dto.request.PostLoginReq;
import com.example.practicespring.dto.request.PostUsersReq;
import com.example.practicespring.dto.request.PutUserImgReq;
import com.example.practicespring.dto.response.GetUserRes;
import com.example.practicespring.dto.response.PostLoginRes;
import com.example.practicespring.dto.response.PostUsersRes;
import com.example.practicespring.entity.UsersImage;
import com.example.practicespring.entity.Users;
import com.example.practicespring.repository.UserImageRepository;
import com.example.practicespring.repository.UsersRepository;
import com.example.practicespring.utils.JwtService;
import com.example.practicespring.utils.Secret;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final JwtService jwtService;

    //회원가입
    public PostUsersRes joinUser(PostUsersReq postUsersReq) throws BaseException {
        try {
            if (userRepository.findUserByEmail(postUsersReq.getEmail()) != null) {
                throw new BaseException(BaseResponseStatus.POST_USERS_EXISTS_EMAIL);
            }
            Users users = new Users();
            users.createUsers(postUsersReq.getEmail(), postUsersReq.getPassword(), postUsersReq.getNickname());
            userRepository.save(users);
            //image default
            UsersImage userImage = new UsersImage();
            userImage.createImage(users);
            userImageRepository.save(userImage);

            return new PostUsersRes(users.getEmail(), users.getNickname(), users.getManner_rate());
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public PostLoginRes login(PostLoginReq postLoginReq) throws BaseException {
        Users user = userRepository.findUserByEmail(postLoginReq.getEmail());
        String password = user.getPassword();
        if (postLoginReq.getPassword().equals(password)) {
            String jwt = jwtService.createJwt(user.getId());
            return new PostLoginRes(user.getId(), jwt);
        } else {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }

    // 전체 회원 정보
    public List<GetUserRes> getUsers() throws BaseException {
        try {
            List<Users> users = userRepository.findUsers();

            List<GetUserRes> GetUserRes = users.stream()
                    .map(user -> new GetUserRes(user.getEmail(), user.getPassword(), user.getNickname(), user.getManner_rate(),
                            userImageRepository.findUrl(user.getId())))
                    .collect(Collectors.toList());
            return GetUserRes;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    //닉네임으로 유저 검색
    public List<GetUserRes> getUsersByNickname(String nickName) throws BaseException {
        try {
            List<Users> users = userRepository.findUsersByNickname(nickName);
            List<GetUserRes> GetuserRes = users.stream()
                    .map(user -> new GetUserRes(user.getEmail(), user.getPassword(), user.getNickname(), user.getManner_rate(),
                            userImageRepository.findUrl(user.getId())))
                    .collect(Collectors.toList());
            return GetuserRes;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    //닉네임 수정
    @Transactional
    public void modifyUserName(PatchUserReq patchUserReq) {
        Users users = userRepository.getReferenceById(patchUserReq.getUserId());
        users.updateNickName(patchUserReq.getNickName());
    }

    //이미지 수정/등록
    @Transactional
    public void putUserImg(PutUserImgReq putUserImgReq) {
        UsersImage usersImage = userImageRepository.getReferenceById(putUserImgReq.getUserId());
        usersImage.putImg(putUserImgReq.getImg_url());
    }
}
