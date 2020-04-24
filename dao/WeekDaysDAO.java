package com.sofct.sofct.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sofct.sofct.model.Configuration;
import com.sofct.sofct.model.WeekDays;

@Repository
public interface WeekDaysDAO extends JpaRepository<WeekDays, Integer> {

    public WeekDays findByName(String name); 
	
}
