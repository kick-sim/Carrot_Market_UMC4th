package com.example.practicespring.repository;

import com.example.practicespring.entity.Area;
import com.example.practicespring.entity.Product;
import com.example.practicespring.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    @Query("select a from Area a where a.zip_code = :zipCode")
    Area findAreaByZipCode(@Param("zipCode") String zipCode);
}
