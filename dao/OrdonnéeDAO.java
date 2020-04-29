package com.sofct.sofct.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofct.sofct.model.Chart;
import com.sofct.sofct.model.DayNumber;
import com.sofct.sofct.model.Ordonnee;

public interface OrdonnéeDAO extends JpaRepository<Ordonnee, Integer> {

	List<Ordonnee> getByChart(Chart chart);

}
