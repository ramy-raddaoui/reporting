package com.sofct.sofct.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofct.sofct.model.Chart;
import com.sofct.sofct.model.DayNumber;
import com.sofct.sofct.model.Ordonnée;

public interface OrdonnéeDAO extends JpaRepository<Ordonnée, Integer> {

	List<Ordonnée> getByChart(Chart chart);

}
