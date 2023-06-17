package com.example.practicespring.controller;

import com.example.practicespring.config.BaseException;
import com.example.practicespring.config.BaseResponse;
import com.example.practicespring.config.BaseResponseStatus;
import com.example.practicespring.dto.request.*;
import com.example.practicespring.dto.response.*;
import com.example.practicespring.entity.Product;
import com.example.practicespring.entity.Users;
import com.example.practicespring.repository.ProductRepository;
import com.example.practicespring.repository.UsersRepository;
import com.example.practicespring.service.ProductService;
import com.example.practicespring.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final UsersRepository usersRepository;
    private final ProductRepository productRepository;
    private final JwtService jwtService;

    //카테고리 등록
    @PostMapping("/catenroll")
    public BaseResponse<PostCategoryRes> enrollCategory(@RequestBody PostCategoryReq postCategoryReq) {
        try {
            System.out.println(postCategoryReq.getCatename());
            if (postCategoryReq.getCatename() == null) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_REQ);
            }
            return new BaseResponse<>(productService.enrollCategory(postCategoryReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //상품등록
    @PostMapping("/enroll")
    public BaseResponse<PostProductRes> enrollProduct(@RequestBody PostProductReq postProductReq) {
        try {
            Users user = usersRepository.findUserByEmail(postProductReq.getSellerEmail());
            //jwt에서 idx 추출.
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (user.getId() != userIdxByJwt) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            //같다면 상품 등록
            if (postProductReq.getTitle() == null || postProductReq.getCategory() == null || postProductReq.getZipCode() == null || postProductReq.getSellerEmail() == null || (Integer) postProductReq.getPrice() == null) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_REQ);
            }
            return new BaseResponse<>(productService.enrollProduct(postProductReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //상품 조회
    @GetMapping("/read")
    public BaseResponse<List<GetProductRes>> getProducts(@RequestParam(required = false) String email) {

        try {
            if (email == null) { // 전화번호없이 조회 시 전체 검색
                return new BaseResponse<>(productService.getProducts());
            }
            // 전화번호 받으면 그 회원의 상품만 검색
            return new BaseResponse<>(productService.getProductsByEmail(email));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //상품삭제
    @PatchMapping("/delete")
    public BaseResponse<String> deleteProduct(@RequestParam long productId) {
        try {
            if (productRepository.findByProductIdCount(productId) < 1) {
                return new BaseResponse<>(BaseResponseStatus.PRODUCT_NOT_EXIST);
            }
            //jwt에서 idx 추출.
            Long userIdxByJwt = jwtService.getUserIdx();
            Product product = productRepository.getReferenceById(productId);
            Users seller = product.getSeller_id();
            //접근한 사람이 상품 판매자인지 확인
            if (seller.getId() != userIdxByJwt) {
                return new BaseResponse<>(BaseResponseStatus.PRODUCT_INVALID_REQ);
            }
            //판매자라면 삭제 진행
            PatchProductReq patchProductReq = new PatchProductReq(seller.getId(), productId);
            productService.deleteProduct(patchProductReq);
            String result = "상품이 삭제되었습니다;";
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //가격변경
    @PostMapping("/modifyprice")
    public BaseResponse<String> modifyPrice(@RequestBody PostModifyPriceReq postModifyPriceReq) {
        try {
            if (productRepository.findByProductIdCount(postModifyPriceReq.getProductId()) < 1) {
                return new BaseResponse<>(BaseResponseStatus.PRODUCT_NOT_EXIST);
            }
            //jwt에서 idx 추출.
            Long userIdxByJwt = jwtService.getUserIdx();
            Product product = productRepository.getReferenceById(postModifyPriceReq.getProductId());
            Users seller = product.getSeller_id();
            //접근한 사람이 상품 판매자인지 확인
            if (seller.getId() != userIdxByJwt) {
                return new BaseResponse<>(BaseResponseStatus.PRODUCT_INVALID_REQ);
            }
            //판매자라면 수정 진행
            productService.modifyPrice(postModifyPriceReq);
            String result = "상품 가격이 변경되었습니다";
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
