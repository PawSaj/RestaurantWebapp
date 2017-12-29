package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JSONMessageGenerator jsonMessageGenerator;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, JSONMessageGenerator jsonMessageGenerator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jsonMessageGenerator = jsonMessageGenerator;
    }

    public UserDto getUserByEmail(String email) {
        logger.debug("getUserByEmail, email={}", email);
        User user = userRepository.findUserByEmail(email);
        return userMapper.mapToDto(user);
    }

    private UserDto getUserById(Long userId) {
        logger.debug("getUserById, userId={}", userId);
        User user = userRepository.findOne(userId);
        return userMapper.mapToDto(user);
    }

    public String userByEmailAndPasswordExists(String email, String password) {
        logger.debug("userByEmailAndPasswordGetRole, email={}", email);
        return userRepository.userByEmailAndPasswordGetRole(email, password);

    }

    public String registerUser(UserDto userDto) {
        if (isEmailExist(userDto)) {
            logger.debug("registerUser Registration failed - user already exist, email={}", userDto.getEmail());
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.DUPLICATE_EMAIL).toString();
        }
        logger.info("saving user to database, user={}", userDto);
        userDto.setRole("USER");
        userDto.setId(null);
        return saveUserToDatabase(mapUserFromDto(userDto));
    }

    private String saveUserToDatabase(User user) {
        logger.debug("saveUserToDatabase saving user to database, user={}", user);
        userRepository.save(user);
        return jsonMessageGenerator.createSimpleRespons(ResponseMessages.USER_REGISTERED).toString();
    }

    private User mapUserFromDto(UserDto userDto) {
        return userMapper.mapFromDto(userDto);
    }

    public String updateUser(Long userId, UserDto userDto, boolean admin) {
        logger.debug("updateUser, userId={}, user={}", userId, userDto);
        if (userRepository.exists(userId)) {
            logger.debug("updateUser User doesn't exist, wrong id, userId={}, user={}", userId, userDto);
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.NO_USER).toString();
        }

        if(!isSelfUpdate(userId, userDto)) {
            if(!admin) {
                return jsonMessageGenerator.createSimpleRespons(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
            }
            if (isEmailExist(userDto)) {
                logger.debug("updateUser Update failed - user already exist, email={}", userDto.getEmail());
                return jsonMessageGenerator.createSimpleRespons(ResponseMessages.DUPLICATE_EMAIL).toString();
            }
        }


        User userToUpdate = createUpdatedUser(userId, userDto, admin);
        updateUserInThisSession(userId, userToUpdate);
        logger.debug("updateUser Update user to database, user={}", userToUpdate);
        userRepository.save(userToUpdate);

        return jsonMessageGenerator.createSimpleRespons(ResponseMessages.USER_UPDATED).toString();

    }

    private boolean isSelfUpdate(Long userId, UserDto userDto) {
        return userRepository.findOne(userId).getEmail().equals(userDto.getEmail());
    }

    private boolean isEmailExist(UserDto userDto) {
        return userRepository.findUserByEmail(userDto.getEmail()) != null;
    }

    private void updateUserInThisSession(Long userId, User userToUpdate) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(getUserById(userId).getEmail())) {
            logger.debug("updateUserInThisSession Update user in this session, user={}", userToUpdate);//correct logged user info
            Authentication request = new UsernamePasswordAuthenticationToken(userToUpdate.getEmail(), userToUpdate.getPassword(), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(request);
        }
    }

    private User createUpdatedUser(Long userId, UserDto userDto, boolean admin) {
        User userToUpdate = userRepository.findOne(userId);

        User userWithUpdatedData = userMapper.mapFromDto(userDto);
        userToUpdate.setEmail(userWithUpdatedData.getEmail());
        userToUpdate.setUsername(userWithUpdatedData.getUsername());
        userToUpdate.setSurname(userWithUpdatedData.getSurname());
        userToUpdate.setPassword(userWithUpdatedData.getPassword());
        userToUpdate.setPhone(userWithUpdatedData.getPhone());
        userToUpdate.setImage(userWithUpdatedData.getImage());
        if (admin) {
            userToUpdate.setRole(userWithUpdatedData.getRole());
        }

        return userToUpdate;
    }

    public String getAllUsers(String email) {
        logger.debug("getAllUsers");
        List<User> users;
        users = userRepository.findAll();
        users.remove(userRepository.findUserByEmail(email));
        List<UserDto> userDtos = new ArrayList<UserDto>();

        for (User u : users) {
            userDtos.add(userMapper.mapToDto(u));
        }

        logger.debug("getAllUsers, userDtos={}", userDtos);
        return jsonMessageGenerator.generateJSONWithUsers(userDtos);
    }

    public String deleteUser(Long userId, boolean admin) {
        logger.debug("deleteUser Deleting user, userId={}", userId);
        if (userRepository.exists(userId)) {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.NO_USER).toString();
        }

        if (userId.equals(userRepository.findUserByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
            userRepository.delete(userId);
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.OK, "redirect", "/logout").toString();
        } else if (admin) {
            userRepository.delete(userId);
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.OK).toString();
        } else {
            logger.debug("deleteUser Deleting user failed, user try to delete another user, userId={}", userId);
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
    }
}
