package com.sajroz.ai.restaurantwebapp.dao;

import com.sajroz.ai.restaurantwebapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN count(u) > 0 THEN u.role ELSE null END FROM User u WHERE u.email = :email AND u.password = :password")
    String userByEmailAndPasswordGetRole(@Param(value = "email") String email, @Param(value = "password") String password);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(@Param(value = "email") String email);


}
