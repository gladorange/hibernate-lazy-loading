package com.example.demo.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.User;

@Repository
@Transactional
public interface UserCrud extends JpaRepository<User,Long> {


    @Query("SELECT u from User u LEFT JOIN fetch u.apples LEFT JOIN fetch u.oranges LEFT JOIN fetch u.grapes LEFT JOIN fetch u.oranges "
            + "where u.id=:id")
    User getFullyInitialized(@Param("id") Long id);


}

