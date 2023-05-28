package com.example.practicespring.service;

import com.example.practicespring.config.BaseException;
import com.example.practicespring.config.BaseResponseStatus;
import com.example.practicespring.dto.request.PatchProductReq;
import com.example.practicespring.dto.request.PostCategoryReq;
import com.example.practicespring.dto.request.PostModifyPriceReq;
import com.example.practicespring.dto.request.PostProductReq;
import com.example.practicespring.dto.response.GetProductRes;
import com.example.practicespring.dto.response.PostCategoryRes;
import com.example.practicespring.dto.response.PostProductRes;
import com.example.practicespring.entity.Area;
import com.example.practicespring.entity.Product;
import com.example.practicespring.entity.Product_Category;
import com.example.practicespring.entity.Users;
import com.example.practicespring.repository.AreaRepository;
import com.example.practicespring.repository.ProductRepository;
import com.example.practicespring.repository.Product_CategoryRepository;
import com.example.practicespring.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final Product_CategoryRepository product_categoryRepository;
    private final ProductRepository productRepository;
    private final AreaRepository areaRepository;
    private final UsersRepository usersRepository;
    public PostCategoryRes enrollCategory(PostCategoryReq postCategoryReq) throws BaseException {
        try{
            Product_Category product_category = new Product_Category();
            product_category.enrollCategory(postCategoryReq.getCateName());
            product_categoryRepository.save(product_category);
            return new PostCategoryRes(product_category.getCateName());
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public PostProductRes enrollProduct(PostProductReq postProductReq) throws BaseException {
        try{
            Product product = new Product();
            Users user = usersRepository.findUserByPhone_number(postProductReq.getSellerNumber());
            Product_Category product_category = product_categoryRepository.findCateByName(postProductReq.getCategory());
            Area area = areaRepository.findAreaByZipCode(postProductReq.getZipCode());
            product.enrollProduct(user,product_category,area, postProductReq.getTitle(), postProductReq.getPrice(), postProductReq.getContent());
            productRepository.save(product);
            return new PostProductRes(postProductReq.getSellerNumber(),postProductReq.getCategory(),postProductReq.getZipCode(),postProductReq.getTitle(),postProductReq.getPrice(),postProductReq.getContent());
        }catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public List<GetProductRes> getProducts() throws BaseException{
        try{
            List<Product> products = productRepository.findProducts();
            List<GetProductRes> getProductRes = products.stream()
                    .map(product -> new GetProductRes(product.getSeller_id().getNickname(),product.getCategory_id().getCateName(),product.getSelling_area_id().getAddress(),product.getTitle(),product.getPrice(),product.getContent()))
                    .collect(Collectors.toList());
            return getProductRes;
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<GetProductRes> getProductsByNumber(String phoneNumber) throws BaseException{
        try{
            List<Product> products = productRepository.findProductByNumber(phoneNumber);
            List<GetProductRes> getProductRes = products.stream()
                    .map(product -> new GetProductRes(product.getSeller_id().getNickname(),product.getCategory_id().getCateName(),product.getSelling_area_id().getAddress(),product.getTitle(),product.getPrice(),product.getContent()))
                    .collect(Collectors.toList());
            return getProductRes;
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteProduct(PatchProductReq patchProductReq){
        Product product = productRepository.getReferenceById(patchProductReq.getProductId());
        product.deleteProduct();
    }
    @Transactional
    public void modifyPrice(PostModifyPriceReq postModifyPriceReq){
        Product product = productRepository.getReferenceById(postModifyPriceReq.getProductId());
        product.modifyPrice(postModifyPriceReq.getPrice());
    }
}
