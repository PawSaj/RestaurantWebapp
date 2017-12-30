package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dao.TablesRepository;
import com.sajroz.ai.restaurantwebapp.dto.TablesDto;
import com.sajroz.ai.restaurantwebapp.mapping.TablesMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.Tables;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TablesService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TablesRepository tablesRepository;

    private final TablesMapper tablesMapper;

    private final JSONMessageGenerator jsonMessageGenerator;

    @Autowired
    public TablesService(TablesRepository tablesRepository, TablesMapper tablesMapper, JSONMessageGenerator jsonMessageGenerator) {
        this.tablesRepository = tablesRepository;
        this.tablesMapper = tablesMapper;
        this.jsonMessageGenerator = jsonMessageGenerator;
    }

    public String getAllTables() {
        logger.debug("getAllTables");
        List<Tables> tables;
        tables = tablesRepository.findAll();

        List<TablesDto> tablesDto = new ArrayList<>(tables.size());
        for(Tables t: tables) {
            tablesDto.add(tablesMapper.tablesToTablesDto(t));
        }
        logger.debug("getAllTables, tablesDto={}", tablesDto);

        return jsonMessageGenerator.generateJSONWithTables(tablesDto).toString();
    }

    Tables getTable(Long tableId) {
        return tablesRepository.findOne(tableId);
    }

    public String saveTable(Long tableId, TablesDto tableDto) {
        Tables table = tablesMapper.tablesDtoToTables(tableDto);
        String verifyTableDataResponse = verifyTableData(table);
        if(verifyTableDataResponse == null) {
            if (isTableExist(table)){
                logger.warn("addTable Table adding failed - table already exist, tableDto={}", tableDto);
                return jsonMessageGenerator.createSimpleRespons(ResponseMessages.DUPLICATE_TABLE).toString();
            }
            table.setId(tableId);
            tablesRepository.save(table);
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.OK).toString();
        } else {
            return verifyTableDataResponse;
        }
    }

    private String verifyTableData(Tables table) {
        if (table.getTableNumber() == 0) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "tableNumber").toString();
        } else if (table.getSeats() == 0) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "seats").toString();
        }
        return null;
    }

    private boolean isTableExist(Tables table) {
        return tablesRepository.findByTableNumber(table.getTableNumber()).size() > 0;
    }

    public String deleteTable(Long tableId) {
        if (!tablesRepository.exists(tableId)) {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.NO_TABLE).toString();
        }

        tablesRepository.delete(tableId);
        return jsonMessageGenerator.createSimpleRespons(ResponseMessages.OK).toString();
    }
}
