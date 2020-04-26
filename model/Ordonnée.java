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
	 
	  @ManyToOne
	  private Configuration configuration;
	  
	 String metrique;

	public Ordonnée(int id, Chart chart, Configuration configuration, String metrique) {
		this.id = id;
		this.chart = chart;
		this.configuration = configuration;
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
	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	public String getMetrique() {
		return metrique;
	}
	public void setMetrique(String metrique) {
		this.metrique = metrique;
	}

	 
}
