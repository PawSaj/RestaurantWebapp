package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.RestaurantReservationDto;
import com.sajroz.ai.restaurantwebapp.services.RestaurantReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantReservationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RestaurantReservationService restaurantReservationService;

    @Autowired
    public RestaurantReservationController(RestaurantReservationService restaurantReservationService) {
        this.restaurantReservationService = restaurantReservationService;
    }

    @RequestMapping(value = "/restaurantReservation", method = RequestMethod.GET, produces = "application/json")
    public String getUserRestaurantReservations() {
        return restaurantReservationService.getAllRestaurantReservations();
    }

    @RequestMapping(value = "/restaurantReservation/{restaurantReservationId}", method = RequestMethod.GET, produces = "application/json")
    public String getUserRestaurantReservations(@PathVariable Long restaurantReservationId) {
        return restaurantReservationService.getRestaurantReservation(restaurantReservationId);
    }

    @RequestMapping(value = "/reservations/restaurant/{date}", method = RequestMethod.GET, produces = "application/json")
    public String getRestaurantReservationForDate(@PathVariable(value = "date") String date) {
        return restaurantReservationService.getRestaurantReservation(date);
    }

    @RequestMapping(value = "/restaurantReservation", method = RequestMethod.POST, produces = "application/json")
    public String addRestaurantReservation(@RequestBody RestaurantReservationDto restaurantReservationDto) {
        logger.debug("restaurantReservation Inserting to database, restaurantReservationDto={}", restaurantReservationDto);
        return restaurantReservationService.saveRestaurantReservation(restaurantReservationDto, null);
    }

    @RequestMapping(value = "/restaurantReservation/{restaurantReservationId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "application/json")
    public String updateRestaurantReservation(@PathVariable(value = "restaurantReservationId") Long restaurantReservationId, @RequestBody RestaurantReservationDto restaurantReservationDto) {
        logger.debug("updateRestaurantReservation Updating to database, restaurantReservationDto={}", restaurantReservationDto);
        return restaurantReservationService.saveRestaurantReservation(restaurantReservationDto, restaurantReservationId);
    }

    @RequestMapping(value = "/restaurantReservation/{restaurantReservationId}", method = RequestMethod.DELETE, produces = "application/json")
    public String deleteRestaurantReservation(@PathVariable Long restaurantReservationId) {
        logger.debug("deleteRestaurantReservation Deleting restaurantReservation, restaurantReservationId={}", restaurantReservationId);
        return restaurantReservationService.deleteRestaurantReservation(restaurantReservationId);
    }

    @RequestMapping(value = "/admin/restaurantReservation", method = RequestMethod.GET, produces = "application/json")
    public String adminGetAllRestaurantReservations() {
        return restaurantReservationService.getAllRestaurantReservations();
    }

    @RequestMapping(value = "/admin/restaurantReservation/{restaurantReservationId}", method = RequestMethod.GET, produces = "application/json")
    public String adminGetUserRestaurantReservations(@PathVariable(value = "restaurantReservationId") Long restaurantReservationId) {
        return restaurantReservationService.getRestaurantReservation(restaurantReservationId);
    }

    @RequestMapping(value = "/admin/restaurantReservation", method = RequestMethod.POST, produces = "application/json")
    public String adminAddRestaurantReservation(@RequestBody RestaurantReservationDto restaurantReservationDto) {
        logger.debug("restaurantReservation Inserting to database, restaurantReservationDto={}", restaurantReservationDto);
        return restaurantReservationService.saveRestaurantReservation(restaurantReservationDto, null);
    }

    @RequestMapping(value = "/admin/restaurantReservation/{restaurantReservationId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "application/json")
    public String adminUpdateUserRestaurantReservation(@PathVariable(value = "restaurantReservationId") Long restaurantReservationId, @RequestBody RestaurantReservationDto restaurantReservationDto) {
        logger.info("adminUpdateUser Updating by admin to, restaurantReservation={}", restaurantReservationDto);
        return restaurantReservationService.saveRestaurantReservation(restaurantReservationDto, restaurantReservationId);
    }

    @RequestMapping(value = "/admin/restaurantReservation/{restaurantReservationId}", method = RequestMethod.DELETE, produces = "application/json")
    public String adminDeleteUserRestaurantReservation(@PathVariable Long restaurantReservationId) {
        logger.debug("adminDeleteUserRestaurantReservation Deleting restaurantReservation, restaurantReservationId={}", restaurantReservationId);
        return restaurantReservationService.deleteRestaurantReservation(restaurantReservationId);
    }

}
