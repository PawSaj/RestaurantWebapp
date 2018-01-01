package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dao.TableReservationRepository;
import com.sajroz.ai.restaurantwebapp.dto.TableReservationDto;
import com.sajroz.ai.restaurantwebapp.mapping.TableReservationMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.TableReservation;
import com.sajroz.ai.restaurantwebapp.model.entity.User;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
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

    private final JSONMessageGenerator jsonMessageGenerator;

    @Autowired
    public TableReservationService(TableReservationRepository tableReservationRepository, TableReservationMapper tableReservationMapper, UserService userService, TablesService tablesService, JSONMessageGenerator jsonMessageGenerator) {
        this.tableReservationRepository = tableReservationRepository;
        this.tableReservationMapper = tableReservationMapper;
        this.userService = userService;
        this.tablesService = tablesService;
        this.jsonMessageGenerator = jsonMessageGenerator;
    }

    public String getAllTableReservations() {
        List<TableReservation> tableReservations;
        User user = userService.getUserByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(hasAdminRole()){
            tableReservations = tableReservationRepository.findAll();
        } else {
            tableReservations = tableReservationRepository.findAllByUser(user);
        }

        List<TableReservationDto> tableReservationDto = new ArrayList<>(tableReservations.size());
        for (TableReservation t : tableReservations) {
            tableReservationDto.add(tableReservationMapper.tableReservationToTableReservationDto(t));
        }

        if (tableReservationDto.isEmpty()) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESERVATIONS).toString();
        }

        if(hasAdminRole()){
            return jsonMessageGenerator.generateJSONWithTableReservationsForAdmin(tableReservationDto).toString();
        } else {
            return jsonMessageGenerator.generateJSONWithTableReservationsForUser(tableReservationDto).toString();
        }
    }

    public String getTableReservation(Long tableReservationId) {
        TableReservationDto tableReservationDto = tableReservationMapper.tableReservationToTableReservationDto(tableReservationRepository.findOne(tableReservationId));
        if (tableReservationDto == null) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESERVATIONS).toString();
        }

        List<TableReservationDto> tableReservationDtoList = new ArrayList<>();
        tableReservationDtoList.add(tableReservationDto);
        if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(tableReservationDto.getUser().getEmail())) {
            return jsonMessageGenerator.generateJSONWithTableReservationsForUser(tableReservationDtoList).toString();
        } else if(hasAdminRole()) {
            return jsonMessageGenerator.generateJSONWithTableReservationsForAdmin(tableReservationDtoList).toString();
        } else {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
    }

    public String getReservedTables(OffsetDateTime startWeekDate) {
        List<TableReservation> tableReservations;
        tableReservations = tableReservationRepository.getReservedTablesInWeek(startWeekDate);
        if (tableReservations.isEmpty()) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESERVATIONS).toString();
        }

        List<TableReservationDto> tableReservationDto = new ArrayList<>(tableReservations.size());
        for (TableReservation t : tableReservations) {
            tableReservationDto.add(tableReservationMapper.tableReservationToTableReservationDto(t));
        }

        return jsonMessageGenerator.generateJSONWithTableReservationsForUser(tableReservationDto).toString();
    }

    public String saveTableReservation(TableReservationDto tableReservationDto, Long tableReservationId) {
        logger.debug("saveTableReservation Saving tableReservation to database, tableReservationDto={}", tableReservationDto);

        if(!hasAdminRole() && tableReservationId != null) {
            if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(tableReservationRepository.findOne(tableReservationId).getUser().getEmail())) {
                return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
            }
        }
        if (tableReservationDto.getTable() == null || tableReservationDto.getTable().getId() == null) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "Table info.").toString();
        } else if (tableReservationDto.getTableReservationDate() == null) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "Reservation date.").toString();
        }
        if (!isTableExist(tableReservationDto.getTable().getId())) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_TABLE).toString();
        }

        OffsetDateTime oldTimeOfTableReservation = null;
        List<TableReservation> tableReservationsInDate = checkTableIsFree(tableReservationDto.getTableReservationDate(), tableReservationDto.getTable().getId());
        int indexOfTableReservationInOldDate = -1;
        if(tableReservationId!= null) {
            TableReservation tableReservationToUpdate = tableReservationRepository.findOne(tableReservationId);

            indexOfTableReservationInOldDate = checkUpdateIsInOldDate(tableReservationToUpdate, tableReservationsInDate);
            if (indexOfTableReservationInOldDate >= 0) {
                oldTimeOfTableReservation = tableReservationsInDate.get(indexOfTableReservationInOldDate).getTableReservationDate();
            }
        }

        if (tableReservationsInDate.isEmpty()
                || (oldTimeOfTableReservation != null && tableReservationDto.getTableReservationDate().isAfter(oldTimeOfTableReservation) && indexOfTableReservationInOldDate == tableReservationsInDate.size() - 1)
                || (oldTimeOfTableReservation != null && tableReservationDto.getTableReservationDate().isBefore(oldTimeOfTableReservation) && indexOfTableReservationInOldDate == 0)) {
            TableReservation tableReservation = tableReservationMapper.tableReservationDtoToTableReservation(tableReservationDto);
            if(hasAdminRole()) {
                if (tableReservation.getUser() == null || tableReservation.getUser().getId() == null) {
                    return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "User info.").toString();
                } else {
                    tableReservation.setUser(userService.getUserById(tableReservation.getUser().getId()));
                }
            } else {
                tableReservation.setUser(userService.getUserByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
            }
            tableReservation.setTable(tablesService.getTable(tableReservation.getTable().getId()));
            tableReservation.setId(tableReservationId);
            tableReservationRepository.save(tableReservation);
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.OK).toString();
        } else {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.TABLE_OCCUPIED).toString();
        }
    }

    private int checkUpdateIsInOldDate(TableReservation tableReservationToUpdate, List<TableReservation> tableReservationsInDate) {
        for (TableReservation t : tableReservationsInDate) {
            if (t.getId().equals(tableReservationToUpdate.getId()) && t.getTable().getId().equals(tableReservationToUpdate.getTable().getId())) {
                return tableReservationsInDate.indexOf(t);
            }
        }
        return -1;
    }

    private boolean isTableExist(Long tableId) {
        return tableReservationRepository.exists(tableId);
    }

    private List<TableReservation> checkTableIsFree(OffsetDateTime tableReservationDate, Long tableId) {
        //TODO
        return tableReservationRepository.checkTableIsFree(tableReservationDate, tableId);
    }

    public String updateTableReservation(TableReservationDto tableReservationDto, Long tableReservationId) {
        logger.debug("updateTableReservation Updating tableReservation to database, tableReservationId={}, tableReservationDto={}", tableReservationId, tableReservationDto);
        if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(tableReservationRepository.findOne(tableReservationId).getUser().getEmail())
                || hasAdminRole()) {
            return saveTableReservation(tableReservationDto, tableReservationId);
        } else {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }

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
            if (tableReservationRepository.exists(tableReservationId)) {
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