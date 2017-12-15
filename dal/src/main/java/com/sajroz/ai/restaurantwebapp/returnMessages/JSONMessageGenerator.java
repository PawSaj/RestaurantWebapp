package com.sajroz.ai.restaurantwebapp.returnMessages;

import com.sajroz.ai.restaurantwebapp.dto.IngredientDto;
import com.sajroz.ai.restaurantwebapp.dto.MealDto;
import com.sajroz.ai.restaurantwebapp.dto.UserDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JSONMessageGenerator {

    public JSONObject createSimpleRespons(Messages message) {
        JSONObject response = new JSONObject();
        response.put("status", message.getCode());
        response.put("description", message.getDescription());
        return response;
    }

    public JSONObject createResponseWithAdditionalInfo(Messages message, String infoKey, String info) {
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
            for (IngredientDto in:  m.getIngredients()) {
                ingredients.put(in.getName());
            }
            jo.put("ingredients", ingredients);
            jo.put("image", m.getImage());
            JSONArray ja = new JSONArray();
            ja.put(jo);
            mainObject.put(m.getId().toString(), ja);
        }
        return mainObject;
    }

    public String generateJSONWithUsers(List<UserDto> userDtos) {
        JSONObject mainObject = new JSONObject();
        for (UserDto u : userDtos) {
            JSONObject jo = new JSONObject();
            jo.put("username", u.getUsername());
            jo.put("surname", u.getSurname());
            jo.put("email", u.getEmail());
            jo.put("password", u.getPassword());
            jo.put("phone", u.getPhone());
            jo.put("role", u.getRole());
            jo.put("image", u.getImage());
            JSONArray ja = new JSONArray();
            ja.put(jo);
            mainObject.put(u.getId().toString(), ja);
        }
        return mainObject.toString();
    }
}
