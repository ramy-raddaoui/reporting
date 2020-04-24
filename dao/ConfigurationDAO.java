package com.sofct.sofct.dao;
import java.util.List;
import com.sofct.sofct.types.getAliasTableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sofct.sofct.model.Configuration;
import com.sofct.sofct.model.TableRef;
import com.sofct.sofct.model.User;

@Repository
public interface ConfigurationDAO extends JpaRepository<Configuration, Integer> {

    //public List<Configuration> findAll();
	

    public Configuration findById(int id); 
    public Configuration save(Configuration config);
    

    
	public List<Configuration> getBytableReferenced(TableRef my_table);
}
      