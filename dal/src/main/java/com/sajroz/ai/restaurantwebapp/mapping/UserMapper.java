package com.sajroz.ai.restaurantwebapp.mapping;

import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import com.sajroz.ai.restaurantwebapp.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserMapper  {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public UserDto mapToDto(User user) {
        logger.debug("map, user={}", user);
        if(user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());
        userDto.setImage(user.getImage());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public User mapFromDto(UserDto userDto) {
        logger.debug("map, user={}", userDto);
        if(userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPassword(userDto.getPassword());
        user.setPhone(userDto.getPhone());
        user.setImage(userDto.getImage());
        user.setRole(userDto.getRole());
        return user;
    }
}
