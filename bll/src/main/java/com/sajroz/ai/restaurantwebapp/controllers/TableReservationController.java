package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.TableReservationDto;
import com.sajroz.ai.restaurantwebapp.services.TableReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
public class TableReservationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TableReservationService tableReservationService;

    @Autowired
    public TableReservationController(TableReservationService tableReservationService) {
        this.tableReservationService = tableReservationService;
    }

    @RequestMapping(value = "/tableReservation", method = RequestMethod.GET, produces = "application/json")
    public String getUserTableReservations() {
        return tableReservationService.getAllTableReservationsByUser();
    }

    @RequestMapping(value = "/tableReservation/{tableReservationId}", method = RequestMethod.GET, produces = "application/json")
    public String getUserTableReservations(@PathVariable Long tableReservationId) {
        return tableReservationService.getTableReservationByUser(tableReservationId);
    }

    @RequestMapping(value = "/getFreeTables/{date}", method = RequestMethod.GET, produces = "application/json")
    public String getFreeTablesForDate(@PathVariable(value = "date") OffsetDateTime date) {
        return tableReservationService.getFreeTables(date);
    }

    @RequestMapping(value = "/tableReservation", method = RequestMethod.POST, produces = "application/json")
    public String addTableReservation(@RequestBody TableReservationDto tableReservationDto) {
        logger.debug("tableReservation Inserting to database, tableReservationDto={}", tableReservationDto);
        return tableReservationService.saveTableReservation(tableReservationDto, null);
    }

    @RequestMapping(value = "/tableReservation/{tableReservationId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "application/json")
    public String updateTableReservation(@PathVariable(value = "tableReservationId") Long tableReservationId, @RequestBody TableReservationDto tableReservationDto) {
        logger.debug("updateTableReservation Updating to database, tableReservationDto={}", tableReservationDto);
        return tableReservationService.updateTableReservation(tableReservationDto, tableReservationId);
    }

    @RequestMapping(value = "/tableReservation/{tableReservationId}", method = RequestMethod.DELETE, produces = "application/json")
    public String deleteTableReservation(@PathVariable Long tableReservationId) {
        logger.debug("deleteTableReservation Deleting tableReservation, tableReservationId={}", tableReservationId);
        return null;
    }

    @RequestMapping(value = "/admin/tableReservation", method = RequestMethod.GET, produces = "application/json")
    public String adminGetAllTableReservations() {
        return null;
    }

    @RequestMapping(value = "/admin/tableReservation/{userId}", method = RequestMethod.GET, produces = "application/json")
    public String adminGetUserTableReservations(@PathVariable(value = "userId") Long userId) {
        return null;
    }

    @RequestMapping(value = "/admin/tableReservation/{tableReservationId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "application/json")
    public String adminUpdateUserTableReservation(@PathVariable(value = "tableReservationId") Long tableReservationId, @RequestBody TableReservationDto tableReservationDto) {
        logger.info("adminUpdateUser Updating by admin to, tableReservation={}", tableReservationDto);
        return null;
    }

    @RequestMapping(value = "/admin/tableReservation/{tableReservationId}", method = RequestMethod.DELETE, produces = "application/json")
    public String adminDeleteUserTableReservation(@PathVariable Long tableReservationId) {
        logger.debug("adminDeleteUserTableReservation Deleting tableReservation, tableReservationId={}", tableReservationId);
        return null;
    }

}
