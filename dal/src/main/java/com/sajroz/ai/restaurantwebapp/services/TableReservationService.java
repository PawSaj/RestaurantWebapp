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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
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

    public String getAllTableReservationsByUser() {
        List<TableReservation> tableReservations;
        User user = userService.getUserByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        tableReservations = tableReservationRepository.findAllByUser(user);

        List<TableReservationDto> tableReservationDto = new ArrayList<>(tableReservations.size());
        for (TableReservation t : tableReservations) {
            tableReservationDto.add(tableReservationMapper.tableReservationToTableReservationDto(t));
        }

        return jsonMessageGenerator.generateJSONWithTableReservationsForUser(tableReservationDto).toString();
    }

    public String getTableReservationByUser(Long tableReservationId) {
        TableReservationDto tableReservationDto = tableReservationMapper.tableReservationToTableReservationDto(tableReservationRepository.findOne(tableReservationId));
        if((SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(tableReservationDto.getUser().getEmail())) {
            return jsonMessageGenerator.convertTableToJSON(tableReservationDto.getTable()).toString();
        } else {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }
    }

    public String getFreeTables(OffsetDateTime date) {
        return null;
    }

    public String saveTableReservation(TableReservationDto tableReservationDto, Long tableReservationId) {
        logger.debug("saveTableReservation Saving tableReservation to database, tableReservationDto={}", tableReservationDto);
        if (tableReservationDto.getTable().getId() == 0) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "tableId").toString();
        }

        if(!isTableExist(tableReservationDto.getTable().getId())) {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.NO_TABLE).toString();
        }

        if(checkTableIsFree(tableReservationDto.getTableReservationDate(), tableReservationDto.getTable().getId()) == null) {
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

    private boolean isTableExist(Long tableId) {
        return tableReservationRepository.exists(tableId);
    }

    private TableReservation checkTableIsFree(OffsetDateTime tableReservationDate, Long tableId) {
        //TODO
        return tableReservationRepository.checkTableIsFree(tableReservationDate, tableId);
    }

    public String updateTableReservation(TableReservationDto tableReservationDto, Long tableReservationId) {
        logger.debug("updateTableReservation Updating tableReservation to database, tableReservationId={}, tableReservationDto={}", tableReservationId, tableReservationDto);

        if((SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals(tableReservationRepository.findOne(tableReservationId).getUser().getEmail())) {
            return saveTableReservation(tableReservationDto, tableReservationId);
        } else {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.ACCESS_TO_USER_ERROR).toString();
        }

    }
}
