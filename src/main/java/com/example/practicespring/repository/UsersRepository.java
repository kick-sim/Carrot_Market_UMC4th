package com.example.practicespring.repository;

import com.example.practicespring.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("select u from Users u")
    List<Users> findUsers();

    @Query("select u from Users u where u.nickname = :nickName")
    List<Users> findUsersByNickname(@Param("nickName") String nickName);

    @Query("select u from Users u where u.email = :email")
    Users findUserByEmail(@Param("email") String email);

    @Query("select count(u) from Users u where u.email = :email")
    Integer findByEmailCount(@Param("email") String email);
}
