package com.sofct.sofct.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sofct.sofct.model.Chart;
import com.sofct.sofct.model.User;

@Repository
public interface ChartDAO extends JpaRepository<Chart, Integer> {

	
	@Query("select id,reportName,reportDesc from Chart where proprietaire = :prop AND tableReferenced=(select id from TableRef where aliasTable=:alias)")
	List<List<String>> findProprietaireCharts(@Param("prop") User utilisateur,@Param("alias") String alias);

	Chart findById(int id);
	
}
 