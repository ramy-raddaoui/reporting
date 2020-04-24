package com.sofct.sofct.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SpecificMail {

	 @Id
     @GeneratedValue
	  private int id;
	 
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	 
	
	 
}
