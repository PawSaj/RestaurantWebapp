package com.sajroz.ai.restaurantwebapp.mapping;

import com.sajroz.ai.restaurantwebapp.dto.RestaurantReservationDto;
import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import com.sajroz.ai.restaurantwebapp.model.entity.RestaurantReservation;
import com.sajroz.ai.restaurantwebapp.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantReservationMapper {

    RestaurantReservation restaurantReservationDtoToRestaurantReservation(RestaurantReservationDto restaurantReservationDto);
    RestaurantReservationDto restaurantReservationToRestaurantReservationDto(RestaurantReservation restaurantReservation);

    UserDto userToUserDto(User user);
}
