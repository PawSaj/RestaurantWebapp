package com.sajroz.ai.restaurantwebapp.mapping;

import com.sajroz.ai.restaurantwebapp.dto.TableReservationDto;
import com.sajroz.ai.restaurantwebapp.dto.TablesDto;
import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import com.sajroz.ai.restaurantwebapp.model.entity.TableReservation;
import com.sajroz.ai.restaurantwebapp.model.entity.Tables;
import com.sajroz.ai.restaurantwebapp.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TableReservationMapper {

    TableReservation tableReservationDtoToTableReservation(TableReservationDto tableReservationDto);
    TableReservationDto tableReservationToTableReservationDto(TableReservation tableReservation);

    UserDto userToUserDto(User user);
    TablesDto tablesToTablesDto(Tables table);
}
