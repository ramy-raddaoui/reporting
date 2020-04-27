package com.sofct.sofct.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofct.sofct.model.DayNumber;
import com.sofct.sofct.model.User;

public interface UserDAO extends JpaRepository<User, Integer> {

	User findByName(String name);
	User findById(int id);
}
