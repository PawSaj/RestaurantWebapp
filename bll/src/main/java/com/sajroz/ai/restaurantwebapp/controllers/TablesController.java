package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.TablesDto;
import com.sajroz.ai.restaurantwebapp.services.TablesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class TablesController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TablesService tablesService;

    @Autowired
    public TablesController(TablesService tablesService) {
        this.tablesService = tablesService;
    }

    @RequestMapping(value = "/admin/tables", method = RequestMethod.GET, produces = "application/json")
    public String sendTables() {
        logger.debug("sendTables Getting all tables.");
        return tablesService.getAllTables();
    }


    @RequestMapping(value = "/admin/tables", method = RequestMethod.POST, produces = "application/json")
    public String addTable(@RequestBody TablesDto tableDto) {
        logger.debug("addTable Adding table to database, table={}", tableDto);
        return tablesService.saveTable(null, tableDto);
    }

    @RequestMapping(value = "/admin/tables/{tableId}", method = RequestMethod.PUT, produces = "application/json")
    public String updateTable(@PathVariable Long tableId, @RequestBody TablesDto tableDto) {
        logger.debug("updateTable Updating table to database tableId={} to table={}", tableId, tableDto);
        return tablesService.saveTable(tableId, tableDto);
    }

    @RequestMapping(value = "/admin/tables/{tableId}", method = RequestMethod.DELETE, produces = "application/json")
    public String deleteTable(@PathVariable Long tableId) {
        logger.debug("deleteTable Delete table from database, tableId={}", tableId);
        return tablesService.deleteTable(tableId);
    }
}
