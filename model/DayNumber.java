package com.sofct.sofct.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="DAYNUMBER")
public class DayNumber {

	 @Id
     @GeneratedValue
	  private int id;
	 
	 @Column(name="day_num")
	 int dayNum;
	 


	public DayNumber(int id, int dayNum) {
		this.id = id;
		this.dayNum = dayNum;
	}
	 
	public DayNumber() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDayNum() {
		return dayNum;
	}

	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}
	 
	 
}
