package com.example.practicespring.controller;

import com.example.practicespring.config.BaseException;
import com.example.practicespring.config.BaseResponse;
import com.example.practicespring.config.BaseResponseStatus;
import com.example.practicespring.dto.request.PatchProductReq;
import com.example.practicespring.dto.request.PostCategoryReq;
import com.example.practicespring.dto.request.PostModifyPriceReq;
import com.example.practicespring.dto.request.PostProductReq;
import com.example.practicespring.dto.response.*;
import com.example.practicespring.entity.Product;
import com.example.practicespring.entity.Users;
import com.example.practicespring.repository.ProductRepository;
import com.example.practicespring.repository.UsersRepository;
import com.example.practicespring.service.ProductService;
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
    public BaseResponse<String> deleteProduct(@RequestParam long userId, @RequestParam long productId) {
        //유저 검색
        Users user = usersRepository.getReferenceById(userId);
        //상품 검색
        Product product = productRepository.getReferenceById(productId);
        // 상품 올린 유저와 요청한 유저가 일치하지 않으면 오류 반환
        if (user.getId() != product.getSeller_id().getId()) {
            return new BaseResponse<>(BaseResponseStatus.PRODUCT_INVALID_REQ);
        }
        PatchProductReq patchProductReq = new PatchProductReq(user.getId(), productId);
        productService.deleteProduct(patchProductReq);
        String result = "상품이 삭제되었습니다;";
        return new BaseResponse<>(result);
    }

    //가격변경
    @PostMapping("/modifyPrice")
    public BaseResponse<String> modifyPrice(@RequestBody PostModifyPriceReq postModifyPriceReq) {
        //상품 검색
        Product product = productRepository.getReferenceById(postModifyPriceReq.getProductId());
        //변경 요청한 유저 Id와 상품의 판매자 Id가 일치하지 않으면 오류 반환
        if (postModifyPriceReq.getUserId() != product.getSeller_id().getId()) {
            return new BaseResponse<>(BaseResponseStatus.PRODUCT_INVALID_REQ);
        }
        productService.modifyPrice(postModifyPriceReq);
        String result = "상품 가격이 변경되었습니다;";
        return new BaseResponse<>(result);
    }

}
