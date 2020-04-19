package com.sofct.sofct.dao;
import java.util.List;
import com.sofct.sofct.types.getAliasTableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sofct.sofct.model.Configuration;

@Repository
public interface ConfigurationDAO extends JpaRepository<Configuration, Integer> {

    //public List<Configuration> findAll();
	
	
    public List<Configuration> findByAliasTable(String alias);
    public Configuration findById(int id); 
    public Configuration save(Configuration config);
    
    @Query("SELECT DISTINCT aliasTable FROM Configuration")
    List<String> findDistinctAliasTables();
}
    