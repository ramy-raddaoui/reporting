package com.sofct.sofct.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SpecificMail {

	 @Id
     @GeneratedValue
	  private int id;
	 
	  @ManyToOne
	  private Chart chart;
	   
	   
	 String email;
	 
	 SpecificMail(){}

	 
	 
	public SpecificMail(int id, String email) {
		this.id = id;
		this.email = email;
	} 
	
	public SpecificMail(String email) {
		this.email = email;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public Chart getChart() {
		return chart;
	}



	public void setChart(Chart chart) {
		this.chart = chart;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


	
	 
}
