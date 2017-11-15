package com.sajroz.ai.restaurantwebapp.mapping;

import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import com.sajroz.ai.restaurantwebapp.model.entity.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserMapper  {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public UserDto mapToDto(UserDao user) {
        logger.debug("map, user={}", user);
        if(user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setSurname(user.getSurname());
        userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());
        userDto.setImage(user.getImage());
        return userDto;
    }

    public UserDao mapFromDto(UserDto userDto) {
        logger.debug("map, user={}", userDto);
        if(userDto == null) {
            return null;
        }

        UserDao user = new UserDao();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setSurname(userDto.getSurname());
        user.setPassword(userDto.getPassword());
        user.setPhone(userDto.getPhone());
        user.setImage(userDto.getImage());
        return user;
    }
}
