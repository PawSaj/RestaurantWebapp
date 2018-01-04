package com.sajroz.ai.restaurantwebapp.returnMessages;

import com.sajroz.ai.restaurantwebapp.dto.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.List;
import java.util.Map;

@Component
public class JSONMessageGenerator {

    public JSONObject createSimpleResponse(ResponseMessages message) {
        JSONObject response = new JSONObject();
        response.put("status", message.getCode());
        response.put("description", message.getDescription());
        return response;
    }

    public JSONObject createResponseWithAdditionalInfo(ResponseMessages message, String infoKey, String info) {
        JSONObject response = createSimpleResponse(message);
        response.put(infoKey, info);
        return response;
    }

    public JSONObject createResponseWithAdditionalInfo(ResponseMessages message, String infoKey, JSONObject info) {
        JSONObject response = createSimpleResponse(message);
        response.put(infoKey, info);
        return response;
    }

    public JSONArray generateJSONWithMenu(List<MealDto> mealDtos) {
        JSONArray mainObject = new JSONArray();
        String lastCategory = null;
        JSONObject categoryWithMeals = new JSONObject();
        JSONArray mealsInCategory = new JSONArray();
        for (MealDto m : mealDtos) {
            if (lastCategory == null) {
                lastCategory = m.getMealCategory().getName();

                categoryWithMeals.put("category", lastCategory);
            } else if (!lastCategory.equals(m.getMealCategory().getName())) {
                categoryWithMeals.put("body", mealsInCategory);
                mainObject.put(categoryWithMeals);

                categoryWithMeals = new JSONObject();
                mealsInCategory = new JSONArray();
                lastCategory = m.getMealCategory().getName();
                categoryWithMeals.put("category", lastCategory);
            }

            mealsInCategory.put(convertMealToJSON(m));
        }
        categoryWithMeals.put("body", mealsInCategory);
        mainObject.put(categoryWithMeals);
        return mainObject;
    }

    public JSONObject convertMealToJSON(MealDto mealDto) {
        JSONObject mealJSON = new JSONObject();
        mealJSON.put("id", mealDto.getId());
        mealJSON.put("name", mealDto.getName());
        mealJSON.put("price", mealDto.getPrice());
        //mealJSON.put("describe", mealDto.getDescribe());
        JSONArray ingredients = new JSONArray();
        for (IngredientDto in : mealDto.getIngredients()) {
            ingredients.put(in.getName());
        }
        mealJSON.put("ingredients", ingredients);
        mealJSON.put("image", mealDto.getImage());
        JSONArray ja = new JSONArray();
        ja.put(mealJSON);
        return mealJSON;
    }

    public JSONArray generateJSONWithUsers(List<UserDto> userDtos) {
        JSONArray mainObject = new JSONArray();
        for (UserDto u : userDtos) {
            JSONObject jo = convertUserToJSON(u);
            mainObject.put(jo);
        }
        return mainObject;
    }

    public JSONObject convertUserToJSON(UserDto userDto) {
        JSONObject jo = new JSONObject();
        jo.put("id", userDto.getId());
        jo.put("name", userDto.getName());
        jo.put("surname", userDto.getSurname());
        jo.put("email", userDto.getEmail());
        jo.put("password", userDto.getPassword());
        jo.put("phone", userDto.getPhone());
        jo.put("role", userDto.getRole());
        jo.put("image", userDto.getImage());
        return jo;
    }

    public JSONArray generateJSONWithTables(List<TablesDto> tablesDto) {
        JSONArray mainObject = new JSONArray();
        for (TablesDto t : tablesDto) {
            JSONObject jo = convertTableToJSON(t);
            mainObject.put(jo);
        }
        return mainObject;
    }

    public JSONObject convertTableToJSON(TablesDto tablesDto) {
        JSONObject jo = new JSONObject();
        jo.put("id", tablesDto.getId());
        jo.put("tableNumber", tablesDto.getTableNumber());
        jo.put("seats", tablesDto.getSeats());
        //jo.put("x", t.getX());
        //jo.put("y", t.getY());
        jo.put("floor", tablesDto.getFloor());
        //jo.put("isFree", t.isFree());
        return jo;
    }

    public JSONArray generateJSONWithTableReservations(List<TableReservationDto> tableReservationDto, boolean admin) {
        JSONArray mainObject = new JSONArray();
        for (TableReservationDto t : tableReservationDto) {
            mainObject.put(convertTableReservationsToJSON(t, admin));
        }
        return mainObject;
    }

    public JSONArray generateJSONWithRestaurantReservations(List<RestaurantReservationDto> restaurantReservationDto, boolean admin) {
        JSONArray mainObject = new JSONArray();
        for (RestaurantReservationDto r : restaurantReservationDto) {
            mainObject.put(convertRestaurantReservationToJSON(r, admin));
        }
        return mainObject;
    }

    public JSONArray generateJSONWithReservationsForTableView(List<TableReservationDto> tableReservationDto, List<RestaurantReservationDto> restaurantReservationDto) {
        JSONArray mainObject = new JSONArray();
        LocalDate lastInsertedDate = LocalDate.MIN;
        for (TableReservationDto t : tableReservationDto) {
            LocalDate dateOfTableReservation = LocalDate.of(t.getTableReservationDate().getYear(), t.getTableReservationDate().getMonth(), t.getTableReservationDate().getDayOfMonth());
            if (!dateOfTableReservation.isEqual(lastInsertedDate)) {
                RestaurantReservationDto restaurantReservationInDate = restaurantReservationsContainsDate(restaurantReservationDto, dateOfTableReservation);
                lastInsertedDate = dateOfTableReservation;
                if (restaurantReservationInDate == null) {
                    mainObject.put(convertTableReservationsToJSON(t, false));
                }
            }
        }
        for (RestaurantReservationDto r : restaurantReservationDto) {
            mainObject.put(convertRestaurantReservationToJSON(r, false));
        }
        return mainObject;
    }

    private RestaurantReservationDto restaurantReservationsContainsDate(final List<RestaurantReservationDto> list, final LocalDate date) {
        return list.stream().filter(o -> o.getRestaurantReservationDate().isEqual(date)).findFirst().orElse(null);
    }

    public JSONArray generateJSONWithMealsCategories(List<MealCategoryDto> mealCategoryDto) {
        JSONArray mainObject = new JSONArray();
        for (MealCategoryDto m : mealCategoryDto) {
            mainObject.put(m.getName());
        }
        return mainObject;
    }

    public JSONArray generateJSONWithReservationsForRestaurantView(List<TableReservationDto> tableReservationDto, List<RestaurantReservationDto> restaurantReservationDto) {
        JSONArray mainObject = new JSONArray();
        LocalDate lastInsertedDate = LocalDate.MIN;
        for (TableReservationDto t : tableReservationDto) {
            LocalDate dateOfTableReservation = LocalDate.of(t.getTableReservationDate().getYear(), t.getTableReservationDate().getMonth(), t.getTableReservationDate().getDayOfMonth());
            if (!dateOfTableReservation.isEqual(lastInsertedDate)) {
                RestaurantReservationDto restaurantReservationInDate = restaurantReservationsContainsDate(restaurantReservationDto, dateOfTableReservation);
                lastInsertedDate = dateOfTableReservation;
                if (restaurantReservationInDate == null) {
                    restaurantReservationInDate = new RestaurantReservationDto();
                    restaurantReservationInDate.setRestaurantReservationDate(dateOfTableReservation);
                    restaurantReservationDto.add(restaurantReservationInDate);
                }

            }
        }
        for (RestaurantReservationDto r : restaurantReservationDto) {
            mainObject.put(convertRestaurantReservationToJSON(r, false));
        }
        return mainObject;
    }

    public JSONObject convertRestaurantReservationToJSON(RestaurantReservationDto restaurantReservationDto, boolean admin) {
        JSONObject reservation = new JSONObject();
        reservation.put("id", restaurantReservationDto.getId());
        reservation.put("date", restaurantReservationDto.getRestaurantReservationDate().toString());
        if (admin) {
            reservation.put("userId", restaurantReservationDto.getUser().getId());
        }
        reservation.put("floor", restaurantReservationDto.getFloor());
        reservation.put("allDay", true);
        reservation.put("describe", restaurantReservationDto.getDescribe());
        return reservation;
    }

    public JSONObject convertTableReservationsToJSON(TableReservationDto tableReservationDto, boolean admin) {
        JSONObject reservation = new JSONObject();
        reservation.put("id", tableReservationDto.getId());
        reservation.put("date", tableReservationDto.getTableReservationDate().toString());
        if (admin) {
            reservation.put("userId", tableReservationDto.getUser().getId());
        }
        reservation.put("table", convertTableToJSON(tableReservationDto.getTable()));
        return reservation;
    }

    public JSONArray generateJSONWithFrequencyOfTableReservation(Map<Long, Long> frequencyOfTableReservation) {
        JSONArray mainObject = new JSONArray();
        for (Map.Entry<Long, Long> m : frequencyOfTableReservation.entrySet()) {
            if(!m.getKey().equals(0L)) {
                JSONObject tableInfo = new JSONObject();
                tableInfo.put("tableId", m.getKey());
                tableInfo.put("frequency", (double)m.getValue()/frequencyOfTableReservation.get(0L));
                mainObject.put(tableInfo);
            }
        }
        return mainObject;
    }

    public JSONArray generateJSONWithTrafficInRestaurant(List<TrafficStatisticDataDto> trafficInRestaurant) {
        JSONArray mainObject = new JSONArray();

        for(TrafficStatisticDataDto t : trafficInRestaurant) {
            JSONObject date = new JSONObject();
            JSONArray hours = new JSONArray();

            date.put("date", t.getTrafficInDate());
            t.sortMapByKey();
            Long sum = 0L;
            for (Map.Entry<OffsetTime, Long> m : t.getTrafficByHours().entrySet()) {
                JSONObject hourData = new JSONObject();
                hourData.put(m.getKey().toString(), m.getValue());
                sum+=m.getValue();
                hours.put(hourData);
            }
            date.put("sum", sum);
            date.put("body", hours);

            mainObject.put(date);
        }

        return mainObject;
    }

    public JSONArray generateJSONWithMealOrderHistory(List<MealOrderStatisticDataDto> mealOrderStatistic) {
        JSONArray mainObject = new JSONArray();

        for (MealOrderStatisticDataDto mO : mealOrderStatistic) {
            JSONObject date = new JSONObject();
            JSONArray orders = new JSONArray();

            date.put("date", mO.getDate());
            //mO.sortMapByKey();
            Long sum = 0L;
            for (Map.Entry<String, Long> m : mO.getMealOrder().entrySet()) {
                JSONObject mealOrders = new JSONObject();
                mealOrders.put(m.getKey(), m.getValue());
                sum+=m.getValue();
                orders.put(mealOrders);
            }
            date.put("sum", sum);
            date.put("body", orders);

            mainObject.put(date);
        }

        return mainObject;
    }

    public JSONArray generateJSONWithUserReservationStatistic(Map<Long, Long> userTableStats, Map<Long, Long> userRestaurantStats) {
        JSONArray mainObject = new JSONArray();

        mainObject.put(generateStatsFor(userTableStats, "table"));
        mainObject.put(generateStatsFor(userRestaurantStats, "restaurant"));

        return mainObject;
    }

    private JSONObject generateStatsFor(Map<Long, Long> map, String type) {
        JSONObject stats = new JSONObject();

        JSONArray element = new JSONArray();
        stats.put("type", type);
        Long sum = 0L;

        for (Map.Entry<Long, Long> m : map.entrySet()) {
            JSONObject info = new JSONObject();
            info.put(m.getKey().toString(), m.getValue());
            element.put(info);
            sum+= m.getValue();
        }
        stats.put("sum", sum);
        stats.put("body", element);
        return stats;
    }
}





























