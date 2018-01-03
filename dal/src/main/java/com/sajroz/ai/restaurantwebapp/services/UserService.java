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

    public String userInfoResponse(String email) {
        User user = userRepository.findUserByEmail(email);
        return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.OK, "user", jsonMessageGenerator.convertUserToJSON(userMapper.mapToDto(user))).toString();
    }

    public UserDto getUserDtoByEmail(String email) {
        logger.debug("getUserDtoByEmail, email={}", email);
        User user = userRepository.findUserByEmail(email);
        return userMapper.mapToDto(user);
    }

    User getUserByEmail(String email) {
        logger.debug("getUserByEmail, email={}", email);
        return userRepository.findUserByEmail(email);
    }

    private UserDto getUserDtoById(Long userId) {
        logger.debug("getUserDtoById, userId={}", userId);
        User user = userRepository.findOne(userId);
        return userMapper.mapToDto(user);
    }

    public String userByEmailAndPasswordExists(String email, String password) {
        logger.debug("userByEmailAndPasswordGetRole, email={}", email);
        return userRepository.userByEmailAndPasswordGetRole(email, password);

    }

    public String registerUser(UserDto userDto) {
        User user = userMapper.mapFromDto(userDto);
        String verifyUserDataResponse = verifyUserData(user);
        if(verifyUserDataResponse == null) {
            if (isEmailExist(user)) {
                logger.debug("registerUser Registration failed - user already exist, email={}", userDto.getEmail());
                return jsonMessageGenerator.createSimpleResponse(ResponseMessages.DUPLICATE_EMAIL).toString();
            }
            logger.info("saving user to database, user={}", user);
            user.setRole("ROLE_USER");
            user.setId(null);
            return saveUserToDatabase(user);
        } else {
            return verifyUserDataResponse;
        }
    }

    private String verifyUserData(User user) {
        if (user.getEmail() == null){
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "email").toString();
        } else if (user.getPassword() == null) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "password").toString();
        } else if (user.getName() == null) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "name").toString();
        } else if (user.getSurname() == null) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "surname").toString();
        } else {
            return null;
        }
    }

    private String saveUserToDatabase(User user) {
        logger.debug("saveUserToDatabase saving user to database, user={}", user);
        userRepository.save(user);
        return jsonMessageGenerator.createSimpleResponse(ResponseMessages.USER_REGISTERED).toString();
    }

    public String updateUser(Long userId, UserDto userDto, boolean admin) {
        logger.debug("updateUser, userId={}, user={}", userId, userDto);
        User user = userMapper.mapFromDto(userDto);
        String verifyUserDataResponse = verifyUserData(user);
        if(verifyUserDataResponse == null) {
            if (!userRepository.exists(userId)) {
                logger.debug("updateUser User doesn't exist, wrong id, userId={}, user={}", userId, userDto);
                return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_USER).toString();
            }

            if (!isSelfUpdate(userId, user)) {
                if (!admin) {
                    return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
                }
                if (isEmailExist(user)) {
                    logger.debug("updateUser Update failed - user already exist, email={}", userDto.getEmail());
                    return jsonMessageGenerator.createSimpleResponse(ResponseMessages.DUPLICATE_EMAIL).toString();
                }
            }

            User userToUpdate = createUpdatedUser(userId, user, admin);
            updateUserInThisSession(userId, userToUpdate);
            logger.debug("updateUser Update user to database, user={}", userToUpdate);
            userRepository.save(userToUpdate);

            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.USER_UPDATED).toString();
        } else {
            return verifyUserDataResponse;
        }


    }

    private boolean isSelfUpdate(Long userId, User user) {
        return userRepository.findOne(userId).getEmail().equals(user.getEmail());
    }

    private boolean isEmailExist(User user) {
        return userRepository.findUserByEmail(user.getEmail()) != null;
    }

    boolean isUserExist(Long userId) {
        return userRepository.exists(userId);
    }
    private void updateUserInThisSession(Long userId, User userToUpdate) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(getUserDtoById(userId).getEmail())) {
            logger.debug("updateUserInThisSession Update user in this session, user={}", userToUpdate);//correct logged user info
            Authentication request = new UsernamePasswordAuthenticationToken(userToUpdate.getEmail(), userToUpdate.getPassword(), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(request);
        }
    }

    private User createUpdatedUser(Long userId, User userWithUpdatedData, boolean admin) {
        User userToUpdate = userRepository.findOne(userId);

        userToUpdate.setEmail(userWithUpdatedData.getEmail());
        userToUpdate.setName(userWithUpdatedData.getName());
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
        List<UserDto> userDtos = new ArrayList<>(users.size());

        for (User u : users) {
            userDtos.add(userMapper.mapToDto(u));
        }

        logger.debug("getAllUsers, userDtos={}", userDtos);
        return jsonMessageGenerator.generateJSONWithUsers(userDtos).toString();
    }

    public String deleteUser(Long userId, boolean admin) {
        logger.debug("deleteUser Deleting user, userId={}", userId);
        if (!userRepository.exists(userId)) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_USER).toString();
        }

        if (userId.equals(userRepository.findUserByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
            userRepository.delete(userId);
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.OK, "redirect", "/logout").toString();
        } else if (admin) {
            userRepository.delete(userId);
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.OK).toString();
        } else {
            logger.debug("deleteUser Deleting user failed, user try to delete another user, userId={}", userId);
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
    }

    public String getUser(Long userId) {
        return jsonMessageGenerator.convertUserToJSON(userMapper.mapToDto(userRepository.findOne(userId))).toString();
    }

    User getUserById(Long userId) {
        return userRepository.findOne(userId);
    }
}
