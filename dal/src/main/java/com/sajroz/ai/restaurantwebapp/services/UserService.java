package com.sajroz.ai.restaurantwebapp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sajroz.ai.restaurantwebapp.dao.UserRepository;
import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import com.sajroz.ai.restaurantwebapp.mapping.UserMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.UserDao;

@Service
@Transactional
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    //@PreAuthorize("hasAuthority('ADIMN')")
    public UserDto getUserByEmail(String email) {
        logger.debug("getUserByEmail, email={}", email);
        UserDao user = userRepository.findUserByEmail(email);
        return userMapper.mapToDto(user);
    }

    public boolean userByEmailAndPasswordExists(String email, String password) {
        logger.debug("userByEmailAndPasswordExists, email={}", email);
        if(userRepository.userByEmailAndPasswordExists(email, password)) {
            logger.debug("userByEmailAndPasswordExists user found, email={}, passowrd={}", email, password);
            return true;
        } else {
            logger.debug("userByEmailAndPasswordExists user not found, email={}, passowrd={}", email, password);
            return false;
        }
    }

    public void insertUserToDatabase(UserDao user) {
        logger.debug("insertUserToDatabase saving user to database, user={}", user);
        userRepository.save(user);
    }

    public UserDao mapUserFromDto(UserDto userDto) {
        return userMapper.mapFromDto(userDto);
    }

}
