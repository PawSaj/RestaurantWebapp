package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dao.MealOrderHistoryRepository;
import com.sajroz.ai.restaurantwebapp.dao.MealRepository;
import com.sajroz.ai.restaurantwebapp.dto.MealDto;
import com.sajroz.ai.restaurantwebapp.dto.MealOrderHistoryDto;
import com.sajroz.ai.restaurantwebapp.dto.MealOrderStatisticDataDto;
import com.sajroz.ai.restaurantwebapp.mapping.MealMapper;
import com.sajroz.ai.restaurantwebapp.mapping.MealOrderHistoryMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.Meal;
import com.sajroz.ai.restaurantwebapp.model.entity.MealOrderHistory;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class MealOrderHistoryService {

    private final MealOrderHistoryRepository mealOrderHistoryRepository;

    private final MealRepository mealRepository;

    private final MealOrderHistoryMapper mealOrderHistoryMapper;

    private final MealMapper mealMapper;

    private final JSONMessageGenerator jsonMessageGenerator;

    @Autowired
    public MealOrderHistoryService(MealOrderHistoryRepository mealOrderHistoryRepository, MealMapper mealMapper, JSONMessageGenerator jsonMessageGenerator, MealRepository mealRepository, MealOrderHistoryMapper mealOrderHistoryMapper) {
        this.mealOrderHistoryRepository = mealOrderHistoryRepository;
        this.mealMapper = mealMapper;
        this.jsonMessageGenerator = jsonMessageGenerator;
        this.mealRepository = mealRepository;
        this.mealOrderHistoryMapper = mealOrderHistoryMapper;
    }


    public String generateMealOrderHistory(String startDateString, String endDateString) {
        OffsetDateTime startDate, endDate;
        try {
            startDate = OffsetDateTime.parse(startDateString);
            endDate = OffsetDateTime.parse(endDateString);
        } catch (DateTimeParseException e) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.BAD_DATE_FORMAT).toString();
        }

        List<MealDto> meals = getMeals();
        List<MealOrderHistoryDto> mealOrderHistoryDtoList = getMealOrderHistory(startDate, endDate);

        if (meals.isEmpty()) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_MEAL).toString();
        } else if (mealOrderHistoryDtoList.isEmpty()) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_MEAL_ORDER).toString();
        }

        List<MealOrderStatisticDataDto> mealOrderStatistic = addOrders(meals, mealOrderHistoryDtoList);

        mealOrderStatistic.sort(Comparator.comparing(MealOrderStatisticDataDto::getDate));

        return jsonMessageGenerator.generateJSONWithMealOrderHistory(mealOrderStatistic).toString();
    }

    private List<MealOrderStatisticDataDto> addOrders(List<MealDto> meals, List<MealOrderHistoryDto> mealOrderHistoryDtoList) {
        List<MealOrderStatisticDataDto> mealOrderStatisticList = new ArrayList<>();
        MealOrderStatisticDataDto mealOrderStatisticData = new MealOrderStatisticDataDto(null, meals);
        for (MealOrderHistoryDto mO : mealOrderHistoryDtoList) {
            if (mealOrderStatisticData.getDate() == null) {
                mealOrderStatisticData = new MealOrderStatisticDataDto(mO.getMealOrderDate().toLocalDate(), meals);
                mealOrderStatisticData.getMealOrder().put(mO.getMeal().getName(), mealOrderStatisticData.getMealOrder().get(mO.getMeal().getName()) + 1L);
            } else if (!mealOrderStatisticData.getDate().isEqual(mO.getMealOrderDate().toLocalDate())) {
                mealOrderStatisticList.add(mealOrderStatisticData);
                mealOrderStatisticData = new MealOrderStatisticDataDto(mO.getMealOrderDate().toLocalDate(), meals);
                mealOrderStatisticData.getMealOrder().put(mO.getMeal().getName(), mealOrderStatisticData.getMealOrder().get(mO.getMeal().getName()) + 1L);
            } else {
                mealOrderStatisticData.getMealOrder().put(mO.getMeal().getName(), mealOrderStatisticData.getMealOrder().get(mO.getMeal().getName()) + 1L);
            }
        }
        mealOrderStatisticList.add(mealOrderStatisticData);

        return mealOrderStatisticList;
    }

    private List<MealOrderHistoryDto> getMealOrderHistory(OffsetDateTime startDate, OffsetDateTime endDate) {
        List<MealOrderHistoryDto> mealOrderHistoryDtoList = new ArrayList<>();
        for (MealOrderHistory m : mealOrderHistoryRepository.getReservedTablesInRange(startDate, endDate)) {
            mealOrderHistoryDtoList.add(mealOrderHistoryMapper.mealOrderHistoryToMealOrderHistoryDto(m));
        }
        return mealOrderHistoryDtoList;
    }

    private List<MealDto> getMeals() {
        List<MealDto> meals = new ArrayList<>();
        for (Meal m : mealRepository.findAll()) {
            meals.add(mealMapper.mealToMealDto(m));
        }
        return meals;
    }
}
