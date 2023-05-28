package com.example.practicespring.service;

import com.example.practicespring.config.BaseException;
import com.example.practicespring.config.BaseResponseStatus;
import com.example.practicespring.dto.request.PostAreaReq;
import com.example.practicespring.dto.response.PostAreaRes;
import com.example.practicespring.entity.Area;
import com.example.practicespring.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AreaService {
    private final AreaRepository areaRepository;
    public PostAreaRes enrollArea(PostAreaReq postAreaReq) throws BaseException {
        try{
            Area area = new Area();
            area.enrollArea(postAreaReq.getAddress(), postAreaReq.getZip_code());
            areaRepository.save(area);
            return new PostAreaRes(area.getAddress(),area.getZip_code());
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
