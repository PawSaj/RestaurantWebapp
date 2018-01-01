package com.sajroz.ai.restaurantwebapp.mapping;

import com.sajroz.ai.restaurantwebapp.dto.TablesDto;
import com.sajroz.ai.restaurantwebapp.model.entity.Tables;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TablesMapper {

    Tables tablesDtoToTables(TablesDto tableDto);
    TablesDto tablesToTablesDto (Tables table);
}
