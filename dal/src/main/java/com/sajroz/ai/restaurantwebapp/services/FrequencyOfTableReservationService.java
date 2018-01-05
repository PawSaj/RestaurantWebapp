package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dao.TableReservationRepository;
import com.sajroz.ai.restaurantwebapp.dao.TrafficHistoryRepository;
import com.sajroz.ai.restaurantwebapp.dto.TableReservationDto;
import com.sajroz.ai.restaurantwebapp.dto.TrafficHistoryDto;
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
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FrequencyOfTableReservationService {

    private final TableReservationRepository tableReservationRepository;

    private final TableReservationMapper tableReservationMapper;

    private final JSONMessageGenerator jsonMessageGenerator;

    private final TrafficHistoryRepository trafficHistoryRepository;

    private final TrafficHistoryMapper trafficHistoryMapper;

    @Autowired
    public FrequencyOfTableReservationService(JSONMessageGenerator jsonMessageGenerator,
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

    public String generateFrequencyOfTableReservation(String startDateString, String endDateString) {
        OffsetDateTime startDate, endDate;
        LocalTime time = LocalTime.of(0, 0, 0);
        ZoneOffset zoneOffset = ZoneOffset.ofHours(1);
        try {
            startDate = OffsetDateTime.of(LocalDate.parse(startDateString), time, zoneOffset);
            endDate = OffsetDateTime.of(LocalDate.parse(endDateString), time, zoneOffset);
        } catch (DateTimeParseException e) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.BAD_DATE_FORMAT).toString();
        }

        List<TableReservationDto> tableReservations = getTableReservations(startDate, endDate);
        List<TrafficHistoryDto> trafficHistory = getTrafficHistory(startDate, endDate);

        if (tableReservations.isEmpty() && trafficHistory.isEmpty()) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_RESERVATION).toString();
        }

        Map<Long, Long> frequencyOfTableReservation = generateMapWithFrequencyOfTableReservations(tableReservations, trafficHistory);

        return jsonMessageGenerator.generateJSONWithFrequencyOfTableReservation(frequencyOfTableReservation).toString();
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
        for (TableReservation t : tableReservationRepository.getReservedTablesInRange(startDate, endDate)) {
            tableReservations.add(tableReservationMapper.tableReservationToTableReservationDto(t));
        }
        return tableReservations;
    }

    private Map<Long, Long> generateMapWithFrequencyOfTableReservations(List<TableReservationDto> tableReservations, List<TrafficHistoryDto> trafficHistory) {
        Map<Long, Long> frequencyOfTableReservation = new HashMap<>();
        frequencyOfTableReservation = addTableReservationInfoFromTableReservations(frequencyOfTableReservation, tableReservations);
        frequencyOfTableReservation = addTableReservationInfoFromTrafficHistory(frequencyOfTableReservation, trafficHistory);

        frequencyOfTableReservation = calculateAndAddSumOfTableReservations(frequencyOfTableReservation);
        return frequencyOfTableReservation;
    }

    private Map<Long, Long> calculateAndAddSumOfTableReservations(Map<Long, Long> frequencyOfTableReservation) {
        Long sumOfTableReservation = 0L;
        for (Map.Entry<Long, Long> m : frequencyOfTableReservation.entrySet()) {
            sumOfTableReservation += m.getValue();
        }
        frequencyOfTableReservation.put(0L, sumOfTableReservation);
        return frequencyOfTableReservation;
    }

    private Map<Long, Long> addTableReservationInfoFromTrafficHistory(Map<Long, Long> frequencyOfTableReservation, List<TrafficHistoryDto> trafficHistory) {
        for (TrafficHistoryDto th : trafficHistory) {
            if (frequencyOfTableReservation.containsKey(th.getTable().getId())) {
                frequencyOfTableReservation.put(th.getTable().getId(), frequencyOfTableReservation.get(th.getTable().getId()) + 1L);
            } else {
                frequencyOfTableReservation.put(th.getTable().getId(), 1L);
            }
        }
        return frequencyOfTableReservation;
    }

    private Map<Long, Long> addTableReservationInfoFromTableReservations(Map<Long, Long> frequencyOfTableReservation, List<TableReservationDto> tableReservations) {
        for (TableReservationDto tr : tableReservations) {

            if (frequencyOfTableReservation.containsKey(tr.getTable().getId())) {
                frequencyOfTableReservation.put(tr.getTable().getId(), frequencyOfTableReservation.get(tr.getTable().getId()) + 1L);
            } else {
                frequencyOfTableReservation.put(tr.getTable().getId(), 1L);
            }
        }
        return frequencyOfTableReservation;
    }

}
