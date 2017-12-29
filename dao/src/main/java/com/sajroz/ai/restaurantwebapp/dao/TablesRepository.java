package com.sajroz.ai.restaurantwebapp.dao;

import com.sajroz.ai.restaurantwebapp.model.entity.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface TablesRepository extends JpaRepository<Tables, Long> {

    List<Tables> findByTableNumber(short tableNumber);
}
