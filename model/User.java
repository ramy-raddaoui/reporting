package com.sofct.sofct.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class User {

	
	 @Id
     @GeneratedValue
	  private int id;
	 
	 private String name;
	 



	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}
	 
	 
	 public User() {}


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
