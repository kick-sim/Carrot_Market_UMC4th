package com.example.practicespring.repository;

import com.example.practicespring.entity.Product;
import com.example.practicespring.entity.Users;
import com.example.practicespring.entity.UsersImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.status = 1")
    List<Product> findProducts();

    @Query("select p from Product p where p.seller_id.email = :email and p.status = 1")
    List<Product> findProductByEmail(@Param("email") String email);

    @Query("select count(p) from Product p where p.id = :id")
    Integer findByProductIdCount(@Param("id") long id);
}
