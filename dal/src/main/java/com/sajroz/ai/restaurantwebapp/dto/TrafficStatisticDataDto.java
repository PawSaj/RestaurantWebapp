package com.sajroz.ai.restaurantwebapp.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TrafficStatisticDataDto implements Serializable {

    private LocalDate trafficInDate;
    private Map<OffsetTime, Long> trafficByHours = new HashMap<>();

    public LocalDate getTrafficInDate() {
        return trafficInDate;
    }

    public void setTrafficInDate(LocalDate trafficInDate) {
        this.trafficInDate = trafficInDate;
    }

    public Map<OffsetTime, Long> getTrafficByHours() {
        return trafficByHours;
    }

    public void setTrafficByHours(Map<OffsetTime, Long> trafficByHours) {
        this.trafficByHours = trafficByHours;
    }

    public void sortMapByKey() {
        trafficByHours = trafficByHours.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
