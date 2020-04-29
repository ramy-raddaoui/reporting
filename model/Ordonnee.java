package com.sofct.sofct.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Ordonnee {

	 @Id
     @GeneratedValue
	  private int id;
	 
	  @ManyToOne
	  @OnDelete(action = OnDeleteAction.CASCADE)
	  private Chart chart;
	 
	  @ManyToOne
	  private Configuration configuration;
	  
	 String metrique;

	public Ordonnee(int id, Chart chart, Configuration configuration, String metrique) {
		this.id = id;
		this.chart = chart;
		this.configuration = configuration;
		this.metrique = metrique;
	}
	public Ordonnee() {
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
