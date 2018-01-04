package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.services.FrequencyOfTableReservationService;
import com.sajroz.ai.restaurantwebapp.services.MealOrderHistoryService;
import com.sajroz.ai.restaurantwebapp.services.TrafficHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {
    //private final Logger logger = LoggerFactory.getLogger(getClass());

    private final FrequencyOfTableReservationService frequencyOfTableReservationService;

    private final TrafficHistoryService trafficHistoryService;

    private final MealOrderHistoryService mealOrderHistoryService;

    @Autowired
    public StatisticController(FrequencyOfTableReservationService frequencyOfTableReservationService, TrafficHistoryService trafficHistoryService, MealOrderHistoryService mealOrderHistoryService) {
        this.frequencyOfTableReservationService = frequencyOfTableReservationService;
        this.trafficHistoryService = trafficHistoryService;
        this.mealOrderHistoryService = mealOrderHistoryService;
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
        return trafficHistoryService.generateTrafficInRestaurant(startDate, endDate);
    }

    //dzienne potrawy
    @RequestMapping(value = "/management/meal/order/{startDate}/{endDate}", method = RequestMethod.GET, produces = "application/json")
    public String getMealOrderHistory(@PathVariable(value = "startDate") String startDate,
                                      @PathVariable(value = "endDate") String endDate) {
        return mealOrderHistoryService.generateMealOrderHistory(startDate, endDate);
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
