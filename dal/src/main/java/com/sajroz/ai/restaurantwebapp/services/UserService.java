package com.sajroz.ai.restaurantwebapp.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sajroz.ai.restaurantwebapp.dao.UserRepository;
import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import com.sajroz.ai.restaurantwebapp.mapping.UserMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.User;

import java.util.ArrayList;
import java.util.List;

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
        User user = userRepository.findUserByEmail(email);
        return userMapper.mapToDto(user);
    }

    public UserDto getUserById(Long userId) {
        logger.debug("getUserById, userId={}", userId);
        User user = userRepository.findOne(userId);
        return userMapper.mapToDto(user);
    }

    public String userByEmailAndPasswordExists(String email, String password) {
        logger.debug("userByEmailAndPasswordGetRole, email={}", email);
        return userRepository.userByEmailAndPasswordGetRole(email, password);

    }

    public void insertUserToDatabase(User user) {
        logger.debug("insertUserToDatabase saving user to database, user={}", user);
        userRepository.save(user);
    }

    public User mapUserFromDto(UserDto userDto) {
        return userMapper.mapFromDto(userDto);
    }

    public String updateUser(Long userId, UserDto userDto, boolean admin) {
        logger.debug("updateUser, userId={}, user={}", userId, userDto);
        JSONObject returnMessage = new JSONObject();
        User userToUpdate = createUpdatedUser(userId, userDto, admin);
        if (userToUpdate == null) {
            logger.debug("updateUser failed, userId={}, user={}", userId, userDto);
            returnMessage.put("status", -1);
            returnMessage.put("message", "User doesn't exist");
            return returnMessage.toString();
        } else {
            logger.debug("updateUser update user to database, user={}", userToUpdate);
            userRepository.save(userToUpdate);
            returnMessage.put("status", 0);
            returnMessage.put("message", "User updated");
            return returnMessage.toString();
        }

    }

    private User createUpdatedUser(Long userId, UserDto userDto, boolean admin) {
        User userToUpdate = userRepository.findOne(userId);
        if (userToUpdate == null) {
            logger.debug("updateUser failed, userId={}, user={}", userId, userDto);
            return null;
        }
        User userWithUpdatedData = userMapper.mapFromDto(userDto);
        userToUpdate.setEmail(userWithUpdatedData.getEmail());
        userToUpdate.setUsername(userWithUpdatedData.getUsername());
        userToUpdate.setSurname(userWithUpdatedData.getSurname());
        userToUpdate.setPassword(userWithUpdatedData.getPassword());
        userToUpdate.setPhone(userWithUpdatedData.getPhone());
        userToUpdate.setImage(userWithUpdatedData.getImage());
        if(admin) {
            userToUpdate.setRole(userWithUpdatedData.getRole());
        }

        return userToUpdate;
    }

    public String getAllUsers(String email) {
        logger.debug("getAllUsers");
        List<User> users;
        users = userRepository.findAll();
        users.remove(userRepository.findUserByEmail(email));

        List<UserDto> userDtos = new ArrayList<>();

        for (User u : users){
            userDtos.add(userMapper.mapToDto(u));
        }
        logger.debug("getAllUsers, userDtos={}", userDtos);

        return generateJSONWithUsers(userDtos);
    }

    private String generateJSONWithUsers(List<UserDto> userDtos) {
        JSONObject mainObject = new JSONObject();
        for (UserDto u : userDtos) {
            JSONObject jo = new JSONObject();
            jo.put("username", u.getUsername());
            jo.put("surname", u.getSurname());
            jo.put("email", u.getEmail());
            jo.put("password", u.getPassword());
            jo.put("phone", u.getPhone());
            jo.put("role", u.getRole());
            jo.put("image", u.getImage());
            JSONArray ja = new JSONArray();
            ja.put(jo);
            mainObject.put(u.getId().toString(), ja);
        }
        return mainObject.toString();
    }
}
