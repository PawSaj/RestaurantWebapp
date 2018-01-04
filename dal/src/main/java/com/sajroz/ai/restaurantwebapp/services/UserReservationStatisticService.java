package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dao.RestaurantReservationRepository;
import com.sajroz.ai.restaurantwebapp.dao.TableReservationRepository;
import com.sajroz.ai.restaurantwebapp.dto.RestaurantReservationDto;
import com.sajroz.ai.restaurantwebapp.dto.TableReservationDto;
import com.sajroz.ai.restaurantwebapp.mapping.RestaurantReservationMapper;
import com.sajroz.ai.restaurantwebapp.mapping.TableReservationMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.RestaurantReservation;
import com.sajroz.ai.restaurantwebapp.model.entity.TableReservation;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserReservationStatisticService {

    private final TableReservationRepository tableReservationRepository;

    private final TableReservationMapper tableReservationMapper;

    private final RestaurantReservationRepository restaurantReservationRepository;

    private final RestaurantReservationMapper restaurantReservationMapper;

    private final JSONMessageGenerator jsonMessageGenerator;

    @Autowired
    public UserReservationStatisticService(TableReservationRepository tableReservationRepository, TableReservationMapper tableReservationMapper, RestaurantReservationRepository restaurantReservationRepository, RestaurantReservationMapper restaurantReservationMapper, JSONMessageGenerator jsonMessageGenerator) {
        this.tableReservationRepository = tableReservationRepository;
        this.tableReservationMapper = tableReservationMapper;
        this.restaurantReservationRepository = restaurantReservationRepository;
        this.restaurantReservationMapper = restaurantReservationMapper;
        this.jsonMessageGenerator = jsonMessageGenerator;
    }


    public String generateUserReservationStatistic(String startDateString, String endDateString, int topNumber) {
        OffsetDateTime startDate, endDate;
        try {
            startDate = OffsetDateTime.parse(startDateString);
            endDate = OffsetDateTime.parse(endDateString);
        } catch (DateTimeParseException e) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.BAD_DATE_FORMAT).toString();
        }

        List<TableReservationDto> tableReservations = getTableReservations(startDate, endDate);
        List<RestaurantReservationDto> restaurantReservations = getRestaurantReservations(startDate, endDate);

        Map<String, Long> userTableStats = addUserTableStats(tableReservations);
        Map<String, Long> userRestaurantStats = addUserRestaurantStats(restaurantReservations);

        userTableStats = userTableStats.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .limit(topNumber)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        userRestaurantStats = userRestaurantStats.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .limit(topNumber)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return jsonMessageGenerator.generateJSONWithUserReservationStatistic(userTableStats, userRestaurantStats).toString();
    }

    private Map<String, Long> addUserRestaurantStats(List<RestaurantReservationDto> restaurantReservations) {
        Map<String, Long> userTableStats = new HashMap<>();
        for (RestaurantReservationDto r : restaurantReservations) {
            if (userTableStats.containsKey(r.getUser().getName())) {
                userTableStats.put(r.getUser().getName(), userTableStats.get(r.getUser().getName()) + 1L);
            } else {
                userTableStats.put(r.getUser().getName(), 1L);
            }
        }

        return userTableStats;
    }

    private Map<String, Long> addUserTableStats(List<TableReservationDto> userTableStats) {
        Map<String, Long> userRestaurantStats = new HashMap<>();
        for (TableReservationDto t : userTableStats) {
            if (userRestaurantStats.containsKey(t.getUser().getName())) {
                userRestaurantStats.put(t.getUser().getName(), userRestaurantStats.get(t.getUser().getName()) + 1L);
            } else {
                userRestaurantStats.put(t.getUser().getName(), 1L);
            }
        }
        return userRestaurantStats;
    }

    private List<TableReservationDto> getTableReservations(OffsetDateTime startDate, OffsetDateTime endDate) {
        List<TableReservationDto> tableReservations = new ArrayList<>();
        for (TableReservation t : tableReservationRepository.getReservedTablesInRange(startDate, endDate)) {
            tableReservations.add(tableReservationMapper.tableReservationToTableReservationDto(t));
        }
        return tableReservations;
    }

    private List<RestaurantReservationDto> getRestaurantReservations(OffsetDateTime startDate, OffsetDateTime endDate) {
        List<RestaurantReservationDto> restaurantReservations = new ArrayList<>();
        for (RestaurantReservation r : restaurantReservationRepository.getReservedRestaurantInRange(startDate, endDate)) {
            restaurantReservations.add(restaurantReservationMapper.restaurantReservationToRestaurantReservationDto(r));
        }
        return restaurantReservations;
    }
}
