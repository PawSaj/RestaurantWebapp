package com.sajroz.ai.restaurantwebapp.returnMessages;

import com.sajroz.ai.restaurantwebapp.dto.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JSONMessageGenerator {

    public JSONObject createSimpleRespons(ResponseMessages message) {
        JSONObject response = new JSONObject();
        response.put("status", message.getCode());
        response.put("description", message.getDescription());
        return response;
    }

    public JSONObject createResponseWithAdditionalInfo(ResponseMessages message, String infoKey, String info) {
        JSONObject response = createSimpleRespons(message);
        response.put(infoKey, info);
        return response;
    }

    public JSONObject generateJSONWithMenu(List<MealDto> mealDtos) {
        JSONObject mainObject = new JSONObject();
        for (MealDto m : mealDtos) {
            JSONObject jo = new JSONObject();
            jo.put("name", m.getName());
            jo.put("price", m.getPrice());
            jo.put("describe", m.getDescribe());
            JSONArray ingredients = new JSONArray();
            int index = 0;
            for (IngredientDto in : m.getIngredients()) {
                ingredients.put(index, in.getName());
                index++;
            }
            jo.put("ingredients", ingredients);
            jo.put("image", m.getImage());
            JSONArray ja = new JSONArray();
            ja.put(jo);
            mainObject.put(m.getId().toString(), ja);
        }
        return mainObject;
    }

    public JSONObject generateJSONWithUsers(List<UserDto> userDtos) {
        JSONObject mainObject = new JSONObject();
        for (UserDto u : userDtos) {
            JSONObject jo = convertUserToJSON(u);
            JSONArray ja = new JSONArray();
            ja.put(jo);
            mainObject.put(u.getId().toString(), ja);
        }
        return mainObject;
    }

    private JSONObject convertUserToJSON(UserDto userDto) {
        JSONObject jo = new JSONObject();
        jo.put("id", userDto.getId());
        jo.put("username", userDto.getUsername());
        jo.put("surname", userDto.getSurname());
        jo.put("email", userDto.getEmail());
        jo.put("password", userDto.getPassword());
        jo.put("phone", userDto.getPhone());
        jo.put("role", userDto.getRole());
        jo.put("image", userDto.getImage());
        return jo;
    }

    public JSONObject generateJSONWithTables(List<TablesDto> tablesDto) {
        JSONObject mainObject = new JSONObject();
        for (TablesDto t : tablesDto) {
            JSONObject jo = convertTableToJSON(t);
            JSONArray ja = new JSONArray();
            ja.put(jo);
            mainObject.put(t.getId().toString(), ja);
        }
        return mainObject;
    }

    private JSONObject convertTableToJSON(TablesDto tablesDto) {
        JSONObject jo = new JSONObject();
        jo.put("id", tablesDto.getId());
        jo.put("number", tablesDto.getTableNumber());
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
            reservation.put("user", t.getUser().getId());
            reservation.put("table", convertTableToJSON(t.getTable()));
            mainObject.put(reservation);
        }
        return mainObject;
    }
}
