package com.example.practicespring.service;

import com.example.practicespring.config.BaseException;
import com.example.practicespring.config.BaseResponseStatus;
import com.example.practicespring.dto.response.GetUserRes;
import com.example.practicespring.dto.response.PostLoginRes;
import com.example.practicespring.dto.response.PostUsersRes;
import com.example.practicespring.entity.Users;
import com.example.practicespring.entity.UsersImage;
import com.example.practicespring.repository.UserImageRepository;
import com.example.practicespring.repository.UsersRepository;
import com.example.practicespring.utils.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

    private final UsersRepository usersRepository;

    private final UserImageRepository userImageRepository;
    private final JwtService jwtService;

    public PostLoginRes socialLogin(String code) throws BaseException {
        //Authorization code를 통해 Access Token받아오기
        String accessToken = getAccessToken(code, "google");
        //Resource Server에서 유저 정보 받아오기
        JsonNode userResourceNode = getUserResource(accessToken, "google");
        //유저 정보 출력
        System.out.println("userResourceNode = " + userResourceNode);
        String email = userResourceNode.get("email").asText();
        String nickname = userResourceNode.get("name").asText();
        String img_url = userResourceNode.get("picture").asText();
        String id = userResourceNode.get("id").asText();
        try {
            //회원가입
            if (usersRepository.findByEmailCount(email) < 1) {
                Users users = new Users();
                users.createUsers(email, id, nickname);
                usersRepository.save(users);
                UsersImage userImage = new UsersImage();
                userImage.createImage(users);
                userImage.putImg(img_url);
                userImageRepository.save(userImage);
            }
            //로그인
            Users user = usersRepository.findUserByEmail(email);
            String password = user.getPassword();
            if (id.equals(password)) {
                String jwt = jwtService.createJwt(user.getId());
                return new PostLoginRes(user.getId(), jwt);
            } else {
                throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
            }
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken, String registrationId) {
        String resourceUri = env.getProperty("oauth2." + registrationId + ".resource-uri");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer" + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
}
