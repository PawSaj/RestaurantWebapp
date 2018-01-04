package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.services.FrequencyOfTableReservationService;
import com.sajroz.ai.restaurantwebapp.services.TrafficInRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {
    //private final Logger logger = LoggerFactory.getLogger(getClass());

    private final FrequencyOfTableReservationService frequencyOfTableReservationService;

    private final TrafficInRestaurantService trafficInRestaurantService;

    @Autowired
    public StatisticController(FrequencyOfTableReservationService frequencyOfTableReservationService, TrafficInRestaurantService trafficInRestaurantService) {
        this.frequencyOfTableReservationService = frequencyOfTableReservationService;
        this.trafficInRestaurantService = trafficInRestaurantService;
    }

    //częstotliwość wybierania stolików
    @RequestMapping(value = "/management/reservations/table/frequency/{startDate}/{endDate}", method = RequestMethod.GET, produces = "application/json")
    public String getFrequencyOfTableReservation(@PathVariable(value = "startDate") String startDate,
                                                 @PathVariable(value = "endDate") String endDate) {
        return frequencyOfTableReservationService.generateFrequencyOfTableReservation(startDate, endDate);
    }

    //dziennie + godzinowe obciążenie restauracji
    @RequestMapping(value = "/management/reservations/traffic/{startDate}/{endDate}", method = RequestMethod.GET, produces = "application/json")
    public String getTrafficInRestaurant(@PathVariable(value = "startDate") String startDate,
                                         @PathVariable(value = "endDate") String endDate) {
        return trafficInRestaurantService.generateTrafficInRestaurant(startDate, endDate);
    }

    //dzienne potrawy
    @RequestMapping(value = "/management/2")
    public String test2() {
        return "good";
    }

    //konsument okresu
    @RequestMapping(value = "/management/3")
    public String test3() {
        return "good";
    }

    //roczny raport gorące okresy na restaurację
    @RequestMapping(value = "/management/4")
    public String test4() {
        return "good";
    }

}
