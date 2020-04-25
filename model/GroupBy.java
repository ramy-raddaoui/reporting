package com.sofct.sofct.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class GroupBy {

	 @Id
     @GeneratedValue
	  private int id;
	 
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Condition> conditions;
		
		
	  @ManyToOne
	  private Chart chart;
	  
	  
	 String name;

	 public GroupBy(String name) {
			this.name = name;
		}

	public GroupBy(int id, List<Condition> conditions, String name) {
		this.id = id;
		this.conditions = conditions;
		this.name = name;
	}


	public GroupBy() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
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



}
