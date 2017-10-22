package com.sajroz.ai.restaurantwebapp.dao;

import com.sajroz.ai.restaurantwebapp.model.entity.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepository extends JpaRepository<com.sajroz.ai.restaurantwebapp.model.entity.UserDao, Long> {

    @Query("SELECT CASE WHEN count(u) > 0 THEN 'true' ELSE 'false' END FROM UserDao u WHERE u.username = :username AND u.password = :password")
    boolean userByNameAndPasswordExists(@Param(value = "username") String username, @Param(value = "password") String password);

    @Query("SELECT u FROM UserDao u WHERE u.username = :username")
    UserDao findUserByUsername(@Param(value = "username") String username);


}
