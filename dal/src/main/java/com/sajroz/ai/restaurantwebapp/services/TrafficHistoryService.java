package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dao.TableReservationRepository;
import com.sajroz.ai.restaurantwebapp.dao.TrafficHistoryRepository;
import com.sajroz.ai.restaurantwebapp.dto.TableReservationDto;
import com.sajroz.ai.restaurantwebapp.dto.TrafficHistoryDto;
import com.sajroz.ai.restaurantwebapp.dto.TrafficStatisticDataDto;
import com.sajroz.ai.restaurantwebapp.mapping.TableReservationMapper;
import com.sajroz.ai.restaurantwebapp.mapping.TrafficHistoryMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.TableReservation;
import com.sajroz.ai.restaurantwebapp.model.entity.TrafficHistory;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.IntStream;

@Service
@Transactional
public class TrafficHistoryService {

    private final TableReservationRepository tableReservationRepository;

    private final TableReservationMapper tableReservationMapper;

    private final JSONMessageGenerator jsonMessageGenerator;

    private final TrafficHistoryRepository trafficHistoryRepository;

    private final TrafficHistoryMapper trafficHistoryMapper;

    @Autowired
    public TrafficHistoryService(JSONMessageGenerator jsonMessageGenerator,
                                 TableReservationRepository tableReservationRepository,
                                 TableReservationMapper tableReservationMapper,
                                 TrafficHistoryRepository trafficHistoryRepository,
                                 TrafficHistoryMapper trafficHistoryMapper) {
        this.jsonMessageGenerator = jsonMessageGenerator;
        this.tableReservationRepository = tableReservationRepository;
        this.tableReservationMapper = tableReservationMapper;
        this.trafficHistoryRepository = trafficHistoryRepository;
        this.trafficHistoryMapper = trafficHistoryMapper;
    }

    public String generateTrafficInRestaurant(String startDateString, String endDateString) {
        OffsetDateTime startDate, endDate;
        try {
            startDate = OffsetDateTime.parse(startDateString);
            endDate = OffsetDateTime.parse(endDateString);
        } catch (DateTimeParseException e) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.BAD_DATE_FORMAT).toString();
        }
        List<TableReservationDto> tableReservations = getTableReservations(startDate, endDate);
        List<TrafficHistoryDto> trafficHistory = getTrafficHistory(startDate, endDate);

        if(tableReservations.isEmpty() && trafficHistory.isEmpty()) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESERVATION).toString();
        }

        List<TrafficStatisticDataDto> trafficInRestaurant = new ArrayList<>();
        trafficInRestaurant = addTrafficFromTableReservations(tableReservations, trafficInRestaurant);
        trafficInRestaurant = addTrafficFromTrafficHistory(trafficHistory, trafficInRestaurant);

        trafficInRestaurant.sort(Comparator.comparing(TrafficStatisticDataDto::getTrafficInDate));

        return jsonMessageGenerator.generateJSONWithTrafficInRestaurant(trafficInRestaurant).toString();
    }

    private List<TrafficStatisticDataDto> addTrafficFromTrafficHistory(List<TrafficHistoryDto> trafficHistory, List<TrafficStatisticDataDto> trafficInRestaurant) {
        OffsetTime time;
        LocalDate date;
        for (TrafficHistoryDto th : trafficHistory) {
            time = th.getTableOccupiedDate().toOffsetTime().withMinute(0).withSecond(0);
            date = th.getTableOccupiedDate().toLocalDate();
            int indexOfTrafficHistoryList = trafficHistoryContainsDateIndex(trafficInRestaurant, date);
            trafficInRestaurant = addElementToTrafficInRestaurant(indexOfTrafficHistoryList, trafficInRestaurant, time, date);
            indexOfTrafficHistoryList = trafficHistoryContainsDateIndex(trafficInRestaurant, date);
            trafficInRestaurant = addElementToTrafficInRestaurant(indexOfTrafficHistoryList, trafficInRestaurant, time.plusHours(1), date);
            trafficInRestaurant = addElementToTrafficInRestaurant(indexOfTrafficHistoryList, trafficInRestaurant, time.plusHours(2), date);
        }
        return trafficInRestaurant;
    }

    private List<TrafficStatisticDataDto> addTrafficFromTableReservations(List<TableReservationDto> tableReservations, List<TrafficStatisticDataDto> trafficInRestaurant) {
        OffsetTime time;
        LocalDate date;
        for (TableReservationDto tr : tableReservations) {
            time = tr.getTableReservationDate().toOffsetTime().withMinute(0).withSecond(0);
            date = tr.getTableReservationDate().toLocalDate();
            int indexOfTrafficHistoryList = trafficHistoryContainsDateIndex(trafficInRestaurant, date);
            trafficInRestaurant = addElementToTrafficInRestaurant(indexOfTrafficHistoryList, trafficInRestaurant, time, date);
            indexOfTrafficHistoryList = trafficHistoryContainsDateIndex(trafficInRestaurant, date);
            trafficInRestaurant = addElementToTrafficInRestaurant(indexOfTrafficHistoryList, trafficInRestaurant, time.plusHours(1), date);
            trafficInRestaurant = addElementToTrafficInRestaurant(indexOfTrafficHistoryList, trafficInRestaurant, time.plusHours(2), date);
        }
        return trafficInRestaurant;
    }

    private List<TrafficStatisticDataDto> addElementToTrafficInRestaurant(int indexOfTrafficHistoryList, List<TrafficStatisticDataDto> trafficInRestaurant, OffsetTime time, LocalDate date) {
        if (indexOfTrafficHistoryList > -1) {
            if (trafficInRestaurant.get(indexOfTrafficHistoryList).getTrafficByHours().containsKey(time)) {
                trafficInRestaurant.get(indexOfTrafficHistoryList).getTrafficByHours().put(time, trafficInRestaurant.get(indexOfTrafficHistoryList).getTrafficByHours().get(time) + 1L);
            } else {
                trafficInRestaurant.get(indexOfTrafficHistoryList).getTrafficByHours().put(time, 1L);
            }
        } else {
            TrafficStatisticDataDto trafficStatisticDataDto = new TrafficStatisticDataDto();
            trafficStatisticDataDto.setTrafficInDate(date);
            Map<OffsetTime, Long> trafficInfo = new HashMap<>();
            trafficInfo.put(time, 1L);
            trafficStatisticDataDto.setTrafficByHours(trafficInfo);
            trafficInRestaurant.add(trafficStatisticDataDto);
        }
        return trafficInRestaurant;
    }

    private int trafficHistoryContainsDateIndex(final List<TrafficStatisticDataDto> list, final LocalDate date) {
        return IntStream.range(0, list.size())
                .filter(i -> date.isEqual(list.get(i).getTrafficInDate()))
                .findFirst().orElse(-1);
    }

    private List<TrafficHistoryDto> getTrafficHistory(OffsetDateTime startDate, OffsetDateTime endDate) {
        List<TrafficHistoryDto> trafficHistory = new ArrayList<>();
        for (TrafficHistory t : trafficHistoryRepository.getReservedTablesInRange(startDate, endDate)) {
            trafficHistory.add(trafficHistoryMapper.trafficHistoryToTrafficHistoryDto(t));
        }
        return trafficHistory;
    }

    private List<TableReservationDto> getTableReservations(OffsetDateTime startDate, OffsetDateTime endDate) {
        List<TableReservationDto> tableReservations = new ArrayList<>();
        for(TableReservation t : tableReservationRepository.getReservedTablesInRange(startDate, endDate)) {
            tableReservations.add(tableReservationMapper.tableReservationToTableReservationDto(t));
        }
        return tableReservations;
    }
}
