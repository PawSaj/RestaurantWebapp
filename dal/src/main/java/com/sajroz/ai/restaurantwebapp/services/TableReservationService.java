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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class TableReservationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TableReservationRepository tableReservationRepository;

    private final TableReservationMapper tableReservationMapper;

    private final UserService userService;

    private final TablesService tablesService;

    private final RestaurantReservationRepository restaurantReservationRepository;

    private final RestaurantReservationMapper restaurantReservationMapper;

    private final JSONMessageGenerator jsonMessageGenerator;

    @Autowired
    public TableReservationService(TableReservationRepository tableReservationRepository, TableReservationMapper tableReservationMapper, UserService userService, TablesService tablesService, JSONMessageGenerator jsonMessageGenerator, RestaurantReservationRepository restaurantReservationRepository, RestaurantReservationMapper restaurantReservationMapper) {
        this.tableReservationRepository = tableReservationRepository;
        this.tableReservationMapper = tableReservationMapper;
        this.userService = userService;
        this.tablesService = tablesService;
        this.jsonMessageGenerator = jsonMessageGenerator;
        this.restaurantReservationRepository = restaurantReservationRepository;
        this.restaurantReservationMapper = restaurantReservationMapper;
    }

    public String getAllTableReservations() {
        List<TableReservation> tableReservations;
        if (hasAdminRole()) {
            tableReservations = tableReservationRepository.findAll();
        } else {
            tableReservations = tableReservationRepository.findAllByUserEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        List<TableReservationDto> tableReservationDto = new ArrayList<>(tableReservations.size());
        for (TableReservation t : tableReservations) {
            tableReservationDto.add(tableReservationMapper.tableReservationToTableReservationDto(t));
        }

        if (tableReservationDto.isEmpty()) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESERVATION).toString();
        }

        return jsonMessageGenerator.generateJSONWithTableReservations(tableReservationDto, hasAdminRole()).toString();
    }

    public String getTableReservation(Long tableReservationId) {
        TableReservationDto tableReservationDto = tableReservationMapper.tableReservationToTableReservationDto(tableReservationRepository.findOne(tableReservationId));
        if (tableReservationDto == null) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESERVATION).toString();
        }

        if (hasAdminRole()) {
            return jsonMessageGenerator.convertTableReservationsToJSON(tableReservationDto, true).toString();
        } else if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(tableReservationDto.getUser().getEmail())) {
            return jsonMessageGenerator.convertTableReservationsToJSON(tableReservationDto, false).toString();
        } else {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
    }

    public String getReservedTables(String date) {
        OffsetDateTime startWeekDate;
        try {
            startWeekDate = OffsetDateTime.parse(date);
        } catch (DateTimeParseException e) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.BAD_DATE_FORMAT).toString();
        }
        List<TableReservation> tableReservations = tableReservationRepository.getReservedTablesInWeek(startWeekDate);
        List<RestaurantReservation> restaurantReservations = restaurantReservationRepository.getReservedRestaurantInWeek(LocalDate.of(startWeekDate.getYear(), startWeekDate.getMonth(), startWeekDate.getDayOfMonth()));
        if (tableReservations.isEmpty() || restaurantReservations.isEmpty()) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESERVATION).toString();
        }

        List<TableReservationDto> tableReservationDto = new ArrayList<>(tableReservations.size());
        List<RestaurantReservationDto> restaurantReservationDto = new ArrayList<>(restaurantReservations.size());
        for (TableReservation t : tableReservations) {
            tableReservationDto.add(tableReservationMapper.tableReservationToTableReservationDto(t));
        }
        for (RestaurantReservation r : restaurantReservations) {
            restaurantReservationDto.add(restaurantReservationMapper.restaurantReservationToRestaurantReservationDto(r));
        }

        return jsonMessageGenerator.generateJSONWithReservationsForTableView(tableReservationDto, restaurantReservationDto).toString();
    }

    public String saveTableReservation(TableReservationDto tableReservationDto, Long tableReservationId) {
        logger.debug("saveTableReservation Saving tableReservation to database, tableReservationDto={}", tableReservationDto);

        String verifyRestaurantReservationDataResponse = checkIfUserTryGetNotHisData(tableReservationId);
        if (verifyRestaurantReservationDataResponse == null) {
            verifyRestaurantReservationDataResponse = verifyRestaurantReservationData(tableReservationDto);
        }


        if (verifyRestaurantReservationDataResponse == null) {
            OffsetDateTime oldTimeOfTableReservation = null;
            List<TableReservation> tableReservationsInDate = getReservationsOnTableInDate(tableReservationDto.getTableReservationDate(), tableReservationDto.getTable().getId());
            int indexOfTableReservationInOldDate = -1;
            if (tableReservationId != null) {
                TableReservation tableReservationToUpdate = tableReservationRepository.findOne(tableReservationId);

                indexOfTableReservationInOldDate = checkUpdateIsInOldDate(tableReservationToUpdate, tableReservationsInDate);
                if (indexOfTableReservationInOldDate >= 0) {
                    oldTimeOfTableReservation = tableReservationsInDate.get(indexOfTableReservationInOldDate).getTableReservationDate();
                }
            }

            if (tableReservationsInDate.isEmpty()
                    || (oldTimeOfTableReservation != null && tableReservationDto.getTableReservationDate().isAfter(oldTimeOfTableReservation) && indexOfTableReservationInOldDate == tableReservationsInDate.size() - 1)
                    || (oldTimeOfTableReservation != null && tableReservationDto.getTableReservationDate().isBefore(oldTimeOfTableReservation) && indexOfTableReservationInOldDate == 0)) {
                return saveTableReservationInDatabase(tableReservationDto, tableReservationId);
            } else {
                return jsonMessageGenerator.createSimpleResponse(ResponseMessages.TABLE_OCCUPIED).toString();
            }
        } else {
            return verifyRestaurantReservationDataResponse;
        }

    }

    private String saveTableReservationInDatabase(TableReservationDto tableReservationDto, Long tableReservationId) {
        TableReservation tableReservation = tableReservationMapper.tableReservationDtoToTableReservation(tableReservationDto);
        if (hasAdminRole()) {
            tableReservation.setUser(userService.getUserById(tableReservation.getUser().getId()));
        } else {
            tableReservation.setUser(userService.getUserByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        }
        tableReservation.setTable(tablesService.getTable(tableReservation.getTable().getId()));
        tableReservation.setId(tableReservationId);
        TableReservationDto finalObject = tableReservationMapper.tableReservationToTableReservationDto(tableReservationRepository.save(tableReservation));
        return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.OK, "data", jsonMessageGenerator.convertTableReservationsToJSON(finalObject, hasAdminRole())).toString();
    }

    private String checkIfUserTryGetNotHisData(Long tableReservationId) {
        if (!hasAdminRole()
                && tableReservationId != null
                && !(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(tableReservationRepository.findOne(tableReservationId).getUser().getEmail())) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
        return null;
    }

    private String verifyRestaurantReservationData(TableReservationDto tableReservationDto) {
        if (tableReservationDto.getTable() == null
                || tableReservationDto.getTable().getId() == null) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "Table info.").toString();
        } else if (tableReservationDto.getTableReservationDate() == null) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "Reservation date.").toString();
        } else if (hasAdminRole()
                && (tableReservationDto.getUser() == null
                || tableReservationDto.getUser().getId() == null)) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "User info.").toString();
        }
        if (!tablesService.isTableExist(tableReservationDto.getTable().getId())) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_TABLE).toString();
        }
        if (hasAdminRole() && !userService.isUserExist(tableReservationDto.getUser().getId())) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_USER).toString();
        }
        if (!checkRestaurantIsFree(LocalDate.of(tableReservationDto.getTableReservationDate().getYear(), tableReservationDto.getTableReservationDate().getMonth(), tableReservationDto.getTableReservationDate().getDayOfMonth()))) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.RESTAURANT_OCCUPIED).toString();
        }
        return null;
    }

    private boolean checkRestaurantIsFree(LocalDate date) {
        return restaurantReservationRepository.checkRestaurantIsFree(date, 0L);
    }

    private int checkUpdateIsInOldDate(TableReservation tableReservationToUpdate, List<TableReservation> tableReservationsInDate) {
        for (TableReservation t : tableReservationsInDate) {
            if (t.getId().equals(tableReservationToUpdate.getId()) && t.getTable().getId().equals(tableReservationToUpdate.getTable().getId())) {
                return tableReservationsInDate.indexOf(t);
            }
        }
        return -1;
    }


    private List<TableReservation> getReservationsOnTableInDate(OffsetDateTime tableReservationDate, Long tableId) {
        return tableReservationRepository.checkTableIsFree(tableReservationDate, tableId);
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

    public String deleteTableReservation(Long tableReservationId) {
        logger.debug("deleteTableReservation Deleting tableReservation from database, tableReservationId={}", tableReservationId);
        if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(tableReservationRepository.findOne(tableReservationId).getUser().getEmail())
                || hasAdminRole()) {
            if (!tableReservationRepository.exists(tableReservationId)) {
                return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_TABLE_RESERVATION).toString();
            }
            tableReservationRepository.delete(tableReservationId);
            logger.debug("deleteTableReservation Deleting tableReservation from database successful");
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.OK).toString();
        } else {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
    }
}
