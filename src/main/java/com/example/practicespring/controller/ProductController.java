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

    @PostMapping("/catenroll")
    public BaseResponse<PostCategoryRes> enrollCategory(@RequestBody PostCategoryReq postCategoryReq) {
        try {
            if(postCategoryReq.getCateName() == null){
                return new BaseResponse<>(BaseResponseStatus.INVALID_REQ);
            }
            return new BaseResponse<>(productService.enrollCategory(postCategoryReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/enroll")
    public BaseResponse<PostProductRes> enrollProduct(@RequestBody PostProductReq postProductReq) {
        try {
            if(postProductReq.getTitle() == null || postProductReq.getCategory() == null || postProductReq.getZipCode() == null || postProductReq.getSellerNumber()== null || (Integer)postProductReq.getPrice() == null){
                return new BaseResponse<>(BaseResponseStatus.INVALID_REQ);
            }
            return new BaseResponse<>(productService.enrollProduct(postProductReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/read")
    public BaseResponse<List<GetProductRes>> getProducts(@RequestParam(required = false) String phoneNumber) {
        try {
            if (phoneNumber == null) {
                return new BaseResponse<>(productService.getProducts());
            }
            return new BaseResponse<>(productService.getProductsByNumber(phoneNumber));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/delete")
    public BaseResponse<String> deleteProduct(@RequestParam long userId, @RequestParam long productId) {
        Users user = usersRepository.getReferenceById(userId);
        Product product = productRepository.getReferenceById(productId);
        if(user.getId() != product.getSeller_id().getId()){
            return new BaseResponse<>(BaseResponseStatus.PRODUCT_INVALID_REQ);
        }
        PatchProductReq patchProductReq = new PatchProductReq(user.getId(), productId);
        productService.deleteProduct(patchProductReq);
        String result = "상품이 삭제되었습니다;";
        return new BaseResponse<>(result);
    }
    @PostMapping("/modifyPrice")
    public BaseResponse<String> modifyPrice(@RequestBody PostModifyPriceReq postModifyPriceReq){
        Product product = productRepository.getReferenceById(postModifyPriceReq.getProductId());
        if(postModifyPriceReq.getUserId() != product.getSeller_id().getId()){
            return new BaseResponse<>(BaseResponseStatus.PRODUCT_INVALID_REQ);
        }
        productService.modifyPrice(postModifyPriceReq);
        String result="상품 가격이 변경되었습니다;";
        return new BaseResponse<>(result);
    }

}
