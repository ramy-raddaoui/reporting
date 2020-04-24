package com.sofct.sofct.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="WEEKDAYS")
public class WeekDays {

	 @Id
     @GeneratedValue
	  private int id;
	 
	 String name;


	    
	 public WeekDays() {}
	 
	 
	public WeekDays(int id, String name) {
		this.id = id;
		this.name = name;
	}


	public int getId() {
		return id;
	}
 
 
	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	 
	 
}
