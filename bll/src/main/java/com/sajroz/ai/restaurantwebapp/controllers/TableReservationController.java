package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.TableReservationDto;
import com.sajroz.ai.restaurantwebapp.services.TableReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        return tableReservationService.getAllTableReservations();
    }

    @RequestMapping(value = "/tableReservation/{tableReservationId}", method = RequestMethod.GET, produces = "application/json")
    public String getUserTableReservations(@PathVariable Long tableReservationId) {
        return tableReservationService.getTableReservation(tableReservationId);
    }

    @RequestMapping(value = "/reservations/table/{date}", method = RequestMethod.GET, produces = "application/json")
    public String getReservedTablesForDate(@PathVariable(value = "date") String date) {
        return tableReservationService.getReservedTables(date);
    }

    @RequestMapping(value = "/tableReservation", method = RequestMethod.POST, produces = "application/json")
    public String addTableReservation(@RequestBody TableReservationDto tableReservationDto) {
        logger.debug("tableReservation Inserting to database, tableReservationDto={}", tableReservationDto);
        return tableReservationService.saveTableReservation(tableReservationDto, null);
    }

    @RequestMapping(value = "/tableReservation/{tableReservationId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "application/json")
    public String updateTableReservation(@PathVariable(value = "tableReservationId") Long tableReservationId, @RequestBody TableReservationDto tableReservationDto) {
        logger.debug("updateTableReservation Updating to database, tableReservationDto={}", tableReservationDto);
        return tableReservationService.saveTableReservation(tableReservationDto, tableReservationId);
    }

    @RequestMapping(value = "/tableReservation/{tableReservationId}", method = RequestMethod.DELETE, produces = "application/json")
    public String deleteTableReservation(@PathVariable Long tableReservationId) {
        logger.debug("deleteTableReservation Deleting tableReservation, tableReservationId={}", tableReservationId);
        return tableReservationService.deleteTableReservation(tableReservationId);
    }

    @RequestMapping(value = "/admin/tableReservation", method = RequestMethod.GET, produces = "application/json")
    public String adminGetAllTableReservations() {
        return tableReservationService.getAllTableReservations();
    }

    @RequestMapping(value = "/admin/tableReservation/{tableReservationId}", method = RequestMethod.GET, produces = "application/json")
    public String adminGetUserTableReservations(@PathVariable(value = "tableReservationId") Long tableReservationId) {
        return tableReservationService.getTableReservation(tableReservationId);
    }

    @RequestMapping(value = "/admin/tableReservation", method = RequestMethod.POST, produces = "application/json")
    public String adminAddTableReservation(@RequestBody TableReservationDto tableReservationDto) {
        logger.debug("tableReservation Inserting to database, tableReservationDto={}", tableReservationDto);
        return tableReservationService.saveTableReservation(tableReservationDto, null);
    }

    @RequestMapping(value = "/admin/tableReservation/{tableReservationId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "application/json")
    public String adminUpdateUserTableReservation(@PathVariable(value = "tableReservationId") Long tableReservationId, @RequestBody TableReservationDto tableReservationDto) {
        logger.info("adminUpdateUser Updating by admin to, tableReservation={}", tableReservationDto);
        return tableReservationService.saveTableReservation(tableReservationDto, tableReservationId);
    }

    @RequestMapping(value = "/admin/tableReservation/{tableReservationId}", method = RequestMethod.DELETE, produces = "application/json")
    public String adminDeleteUserTableReservation(@PathVariable Long tableReservationId) {
        logger.debug("adminDeleteUserTableReservation Deleting tableReservation, tableReservationId={}", tableReservationId);
        return tableReservationService.deleteTableReservation(tableReservationId);
    }

}
