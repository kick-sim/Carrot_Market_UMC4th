package com.example.practicespring.repository;

import com.example.practicespring.entity.UsersImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UsersImage, Long> {
    @Query("select ui.image_url from UsersImage ui where ui.id = :id")
    String findUrl(@Param("id") long id);
}
