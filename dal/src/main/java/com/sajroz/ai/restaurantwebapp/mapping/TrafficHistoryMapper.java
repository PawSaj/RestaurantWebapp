package com.sajroz.ai.restaurantwebapp.mapping;

import com.sajroz.ai.restaurantwebapp.dto.TrafficHistoryDto;
import com.sajroz.ai.restaurantwebapp.model.entity.TrafficHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrafficHistoryMapper {

    TrafficHistory trafficHistoryDtoToTrafficHistory(TrafficHistoryDto trafficHistoryDto);
    TrafficHistoryDto trafficHistoryToTrafficHistoryDto(TrafficHistory trafficHistory);
}
