package com.sajroz.ai.restaurantwebapp.services;


import com.sajroz.ai.restaurantwebapp.dao.RestaurantReservationRepository;
import com.sajroz.ai.restaurantwebapp.dao.TableReservationRepository;
import com.sajroz.ai.restaurantwebapp.dto.RestaurantReservationDto;
import com.sajroz.ai.restaurantwebapp.dto.TableReservationDto;
import com.sajroz.ai.restaurantwebapp.mapping.RestaurantReservationMapper;
import com.sajroz.ai.restaurantwebapp.mapping.TableReservationMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.RestaurantReservation;
import com.sajroz.ai.restaurantwebapp.model.entity.TableReservation;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import com.sajroz.ai.restaurantwebapp.validation.RestaurantReservationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class RestaurantReservationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RestaurantReservationRepository restaurantReservationRepository;

    private final RestaurantReservationMapper restaurantReservationMapper;

    private final TableReservationRepository tableReservationRepository;

    private final TableReservationMapper tableReservationMapper;

    private final UserService userService;

    private final JSONMessageGenerator jsonMessageGenerator;

    @Autowired
    public RestaurantReservationService(RestaurantReservationRepository restaurantReservationRepository, RestaurantReservationMapper restaurantReservationMapper, JSONMessageGenerator jsonMessageGenerator, TableReservationRepository tableReservationRepository, TableReservationMapper tableReservationMapper, UserService userService) {
        this.restaurantReservationRepository = restaurantReservationRepository;
        this.restaurantReservationMapper = restaurantReservationMapper;
        this.jsonMessageGenerator = jsonMessageGenerator;
        this.tableReservationRepository = tableReservationRepository;
        this.tableReservationMapper = tableReservationMapper;
        this.userService = userService;
    }

    public String getAllRestaurantReservations() {
        List<RestaurantReservation> restaurantReservations;
        if (hasAdminRole()) {
            restaurantReservations = restaurantReservationRepository.findAll();
        } else {
            restaurantReservations = restaurantReservationRepository.findAllByUserEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        List<RestaurantReservationDto> restaurantReservationDto = new ArrayList<>(restaurantReservations.size());
        for (RestaurantReservation r : restaurantReservations) {
            restaurantReservationDto.add(restaurantReservationMapper.restaurantReservationToRestaurantReservationDto(r));
        }

        if (restaurantReservationDto.isEmpty()) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESERVATION).toString();
        }

        return jsonMessageGenerator.generateJSONWithRestaurantReservations(restaurantReservationDto, hasAdminRole()).toString();
    }

    private boolean hasAdminRole() {
        @SuppressWarnings("unchecked")
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals("ROLE_ADMIN");
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

    public String getRestaurantReservation(Long restaurantReservationId) {
        RestaurantReservationDto restaurantReservationDto = restaurantReservationMapper.restaurantReservationToRestaurantReservationDto(restaurantReservationRepository.findOne(restaurantReservationId));
        if (restaurantReservationDto == null) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESERVATION).toString();
        }

        if (hasAdminRole()) {
            return jsonMessageGenerator.convertRestaurantReservationToJSON(restaurantReservationDto, true).toString();
        } else if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(restaurantReservationDto.getUser().getEmail())) {
            return jsonMessageGenerator.convertRestaurantReservationToJSON(restaurantReservationDto, false).toString();
        } else {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
    }

    public String getRestaurantReservation(String date) {
        LocalDate startMonthDate;
        try {
            startMonthDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.BAD_DATE_FORMAT).toString();
        }
        List<TableReservation> tableReservations = tableReservationRepository.getReservedTablesInMonth(startMonthDate);
        List<RestaurantReservation> restaurantReservations;
        restaurantReservations = restaurantReservationRepository.getReservedRestaurantInMonth(startMonthDate);
        if (tableReservations.isEmpty() || restaurantReservations.isEmpty()) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESERVATION).toString();
        }
        List<TableReservationDto> tableReservationDto = new ArrayList<>(tableReservations.size());
        List<RestaurantReservationDto> restaurantReservationDto = new ArrayList<>(restaurantReservations.size());
        for (TableReservation t : tableReservations) {
            tableReservationDto.add(tableReservationMapper.tableReservationToTableReservationDto(t));
        }
        for (RestaurantReservation t : restaurantReservations) {
            restaurantReservationDto.add(restaurantReservationMapper.restaurantReservationToRestaurantReservationDto(t));
        }

        return jsonMessageGenerator.generateJSONWithReservationsForRestaurantView(tableReservationDto, restaurantReservationDto).toString();

    }

    public String saveRestaurantReservation(RestaurantReservationDto restaurantReservationDto, Long restaurantReservationId) {
        logger.debug("saveRestaurantReservation Saving tableReservation to database, restaurantReservationDto={}", restaurantReservationDto);
        String verifyRestaurantReservationDataResponse = checkIfUserTryGetNotHisData(restaurantReservationId);
        if (verifyRestaurantReservationDataResponse == null) {
            verifyRestaurantReservationDataResponse = verifyRestaurantReservationData(restaurantReservationDto);
        }
        if (verifyRestaurantReservationDataResponse == null) {
            if (checkRestaurantIsFree(restaurantReservationDto.getRestaurantReservationDate(), restaurantReservationId)) {
                RestaurantReservation restaurantReservation = restaurantReservationMapper.restaurantReservationDtoToRestaurantReservation(restaurantReservationDto);
                if (hasAdminRole()) {
                    restaurantReservation.setUser(userService.getUserById(restaurantReservation.getUser().getId()));
                } else {
                    restaurantReservation.setUser(userService.getUserByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
                }
                restaurantReservation.setId(restaurantReservationId);
                RestaurantReservationDto finalObject = restaurantReservationMapper.restaurantReservationToRestaurantReservationDto(restaurantReservationRepository.save(restaurantReservation));
                return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.OK, "data", jsonMessageGenerator.convertRestaurantReservationToJSON(finalObject, hasAdminRole())).toString();
            } else {
                return jsonMessageGenerator.createSimpleResponse(ResponseMessages.RESTAURANT_OCCUPIED).toString();
            }
        } else {
            return verifyRestaurantReservationDataResponse;
        }


    }

    private String checkIfUserTryGetNotHisData(Long restaurantReservationId) {
        if (!hasAdminRole()
                && restaurantReservationId != null
                && !(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(restaurantReservationRepository.findOne(restaurantReservationId).getUser().getEmail())) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
        return null;
    }

    private String verifyRestaurantReservationData(RestaurantReservationDto restaurantReservationDto) {
        RestaurantReservationValidator validator = new RestaurantReservationValidator(restaurantReservationDto);
        if (restaurantReservationDto.getRestaurantReservationDate() == null) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "Reservation date.").toString();
        } else if (hasAdminRole()
                && (restaurantReservationDto.getUser() == null
                || restaurantReservationDto.getUser().getId() == null)) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "User info.").toString();
        }
        if (hasAdminRole() && !userService.isUserExist(restaurantReservationDto.getUser().getId())) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_USER).toString();
        }
        String validationResult = validator.validateUser();
        if (validationResult != null) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "invalid data", validationResult).toString();
        }
        return null;
    }

    private boolean checkRestaurantIsFree(LocalDate restaurantReservationDate, Long restaurantReservationId) {
        if (restaurantReservationId == null) {
            restaurantReservationId = 0L;
        }
        return restaurantReservationRepository.checkRestaurantIsFree(restaurantReservationDate, restaurantReservationId) && tableReservationRepository.checkIsNoTableReservationOnDay(restaurantReservationDate);
    }

    public String deleteRestaurantReservation(Long restaurantReservationId) {
        logger.debug("deleteRestaurantReservation Deleting restaurantReservation from database, restaurantReservationId={}", restaurantReservationId);
        if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(restaurantReservationRepository.findOne(restaurantReservationId).getUser().getEmail())
                || hasAdminRole()) {
            if (!restaurantReservationRepository.exists(restaurantReservationId)) {
                return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESTAURANT_RESERVATION).toString();
            }
            restaurantReservationRepository.delete(restaurantReservationId);
            logger.debug("deleteRestaurantReservation Deleting restaurantReservation from database successful");
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.OK).toString();
        } else {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
    }
}
