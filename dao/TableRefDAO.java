package com.sofct.sofct.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sofct.sofct.model.TableRef;

public interface TableRefDAO extends JpaRepository<TableRef, Integer> {

	TableRef getByAliasTable(String alias);
	
	
	 @Query("SELECT DISTINCT aliasTable FROM TableRef")
	    List<String> findDistinctAliasTable();
}
 