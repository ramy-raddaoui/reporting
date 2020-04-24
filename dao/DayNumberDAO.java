package com.sofct.sofct.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofct.sofct.model.DayNumber;
import com.sofct.sofct.model.WeekDays;

public interface DayNumberDAO extends JpaRepository<DayNumber, Integer> {

	DayNumber findByDayNum(int daynumber);

}
