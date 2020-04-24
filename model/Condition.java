package com.sofct.sofct.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Condition {

	
	 @Id
     @GeneratedValue
	  private int id;
	 
	 String operator;
	 String value;
	 
	 String logicCond;
	 
	 

	public Condition() {
		// TODO Auto-generated constructor stub
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

	
	 
}
