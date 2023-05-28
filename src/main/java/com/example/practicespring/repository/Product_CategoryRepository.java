package com.example.practicespring.repository;

import com.example.practicespring.entity.Area;
import com.example.practicespring.entity.Product;
import com.example.practicespring.entity.Product_Category;
import com.example.practicespring.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Product_CategoryRepository extends JpaRepository<Product_Category, Long> {
    @Query("select pc from Product_Category pc where pc.CateName = :category")
    Product_Category findCateByName(@Param("category") String category);
}
