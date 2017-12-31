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

        if(hasAdminRole()){
            return jsonMessageGenerator.generateJSONWithTableReservationsForAdmin(tableReservationDto).toString();
        } else {
            return jsonMessageGenerator.generateJSONWithTableReservationsForUser(tableReservationDto).toString();
        }
    }

    public String getTableReservation(Long tableReservationId) {
        TableReservationDto tableReservationDto = tableReservationMapper.tableReservationToTableReservationDto(tableReservationRepository.findOne(tableReservationId));
        List<TableReservationDto> tableReservationDtoList = new ArrayList<>();
        tableReservationDtoList.add(tableReservationDto);
        if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(tableReservationDto.getUser().getEmail())) {
            return jsonMessageGenerator.generateJSONWithTableReservationsForUser(tableReservationDtoList).toString();
        } else if(hasAdminRole()) {
            return jsonMessageGenerator.generateJSONWithTableReservationsForAdmin(tableReservationDtoList).toString();
        } else {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
    }

    public String getReservedTables(OffsetDateTime startWeekDate) {
        List<TableReservation> tableReservations;
        tableReservations = tableReservationRepository.getReservedTablesInWeek(startWeekDate);

        List<TableReservationDto> tableReservationDto = new ArrayList<>(tableReservations.size());
        for (TableReservation t : tableReservations) {
            tableReservationDto.add(tableReservationMapper.tableReservationToTableReservationDto(t));
        }

        return jsonMessageGenerator.generateJSONWithTableReservationsForUser(tableReservationDto).toString();
    }

    public String saveTableReservation(TableReservationDto tableReservationDto, Long tableReservationId) {
        logger.debug("saveTableReservation Saving tableReservation to database, tableReservationDto={}", tableReservationDto);

        if (tableReservationDto.getTable().getId() == null || tableReservationId == null) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "tableId").toString();
        }
        if (!isTableExist(tableReservationDto.getTable().getId())) {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.NO_TABLE).toString();
        }

        TableReservation tableReservationToUpdate = tableReservationRepository.findOne(tableReservationId);
        List<TableReservation> tableReservationsInDate = checkTableIsFree(tableReservationDto.getTableReservationDate(), tableReservationDto.getTable().getId());
        int indexOfTableReservationInOldDate = checkUpdateIsInOldDate(tableReservationToUpdate, tableReservationsInDate);
        OffsetDateTime oldTimeOfTableReservation = null;
        if (indexOfTableReservationInOldDate >= 0) {
            oldTimeOfTableReservation = tableReservationsInDate.get(indexOfTableReservationInOldDate).getTableReservationDate();
        }

        if (tableReservationsInDate == null
                || (oldTimeOfTableReservation != null && tableReservationDto.getTableReservationDate().isAfter(oldTimeOfTableReservation) && indexOfTableReservationInOldDate == tableReservationsInDate.size() - 1)
                || (oldTimeOfTableReservation != null && tableReservationDto.getTableReservationDate().isBefore(oldTimeOfTableReservation) && indexOfTableReservationInOldDate == 0)) {
            TableReservation tableReservation = tableReservationMapper.tableReservationDtoToTableReservation(tableReservationDto);
            tableReservation.setUser(userService.getUserByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
            tableReservation.setTable(tablesService.getTable(tableReservationDto.getTable().getId()));
            tableReservation.setId(tableReservationId);
            tableReservationRepository.save(tableReservation);
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.OK).toString();
        } else {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.TABLE_OCCUPIED).toString();
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
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
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
                return jsonMessageGenerator.createSimpleRespons(ResponseMessages.NO_TABLE_RESERVATION).toString();
            }
            tableReservationRepository.delete(tableReservationId);
            logger.debug("deleteTableReservation Deleting tableReservation from database successful");
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.OK).toString();
        } else {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
    }
}
