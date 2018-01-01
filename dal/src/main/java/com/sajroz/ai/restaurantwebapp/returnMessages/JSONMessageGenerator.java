package com.sajroz.ai.restaurantwebapp.returnMessages;

import com.sajroz.ai.restaurantwebapp.dto.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

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
            } else if(!lastCategory.equals(m.getMealCategory().getName())) {
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

    public JSONArray generateJSONWithTableReservationsForUser(List<TableReservationDto> tableReservationDto) {
        JSONArray mainObject = new JSONArray();
        for (TableReservationDto t : tableReservationDto) {
            JSONObject reservation = new JSONObject();
            reservation.put("id", t.getId());
            reservation.put("date", t.getTableReservationDate().toString());
            reservation.put("table", convertTableToJSON(t.getTable()));
            mainObject.put(reservation);
        }
        return mainObject;
    }

    public JSONArray generateJSONWithTableReservationsForAdmin(List<TableReservationDto> tableReservationDto) {
        JSONArray mainObject = new JSONArray();
        for (TableReservationDto t : tableReservationDto) {
            JSONObject reservation = new JSONObject();
            reservation.put("id", t.getId());
            reservation.put("date", t.getTableReservationDate().toString());
            reservation.put("userId", t.getUser().getId());
            reservation.put("table", convertTableToJSON(t.getTable()));
            mainObject.put(reservation);
        }
        return mainObject;
    }

    public JSONArray generateJSONWithMealsCategories(List<MealCategoryDto> mealCategoryDto) {
        JSONArray mainObject = new JSONArray();
        for(MealCategoryDto m : mealCategoryDto) {
            mainObject.put(m.getName());
        }
        return mainObject;
    }
}
