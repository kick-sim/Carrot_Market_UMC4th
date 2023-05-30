package com.example.practicespring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Users seller_id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Product_Category category_id;
    @ManyToOne
    @JoinColumn(name = "selling_area_id")
    private Area selling_area_id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private int status;
    @Column(nullable = false)
    private int view_count;
    @Column(nullable = false)
    private LocalDateTime created_at;
    @Column(nullable = false)
    private LocalDateTime updated_at;
    @Column(nullable = false)
    private LocalDateTime refreshed_at;

    public void enrollProduct(Users user, Product_Category product_category, Area selling_area_id, String title, int price, String content) {
        this.seller_id = user;
        this.category_id = product_category;
        this.selling_area_id = selling_area_id;
        this.title = title;
        this.price = price;
        this.content = content;
        this.status = 1;
        this.view_count = 0;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
        this.refreshed_at = LocalDateTime.now();
    }

    public void deleteProduct() {
        this.status = 0;
    }

    public void modifyPrice(int price) {
        this.price = price;
    }
}