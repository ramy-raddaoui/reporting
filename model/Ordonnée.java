package com.sofct.sofct.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ordonnée {

	 @Id
     @GeneratedValue
	  private int id;
	 
	  @ManyToOne
	  private Chart chart;
	 
	 String name ;
	 String metrique;
	public Ordonnée(int id, String name, String metrique) {
		this.id = id;
		this.name = name;
		this.metrique = metrique;
		
		
	}
	public Ordonnée() {
		// TODO Auto-generated constructor stub
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMetrique() {
		return metrique;
	}
	public void setMetrique(String metrique) {
		this.metrique = metrique;
	}

	 
	 
}
