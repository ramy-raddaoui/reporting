package com.sofct.sofct.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Condition {

	
	 @Id
     @GeneratedValue
	  private int id;
	 
	 String operator;
	 String value;
	 
	 String logicCond;
	 
	  @ManyToOne()
	  @JoinColumn()
	  private Chart chart;
	  
	  @ManyToOne
	  private GroupBy groupBy;

	public Condition() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Condition(int id, String operator, String value, String logicCond, Chart chart) {
		super();
		this.id = id;
		this.operator = operator;
		this.value = value;
		this.logicCond = logicCond;
		this.chart = chart;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getOperator() {
		return operator;
	}



	public void setOperator(String operator) {
		this.operator = operator;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public String getLogicCond() {
		return logicCond;
	}



	public void setLogicCond(String logicCond) {
		this.logicCond = logicCond;
	}



	public Chart getChart() {
		return chart;
	}



	public void setChart(Chart chart) {
		this.chart = chart;
	}



	public GroupBy getGroupBy() {
		return groupBy;
	}



	public void setGroupBy(GroupBy groupBy) {
		this.groupBy = groupBy;
	}




	
	 
}
