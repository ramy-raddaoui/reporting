package com.sofct.sofct.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TableRef {

	
	 @Id
     @GeneratedValue
	  private int id;
	 
	 String nomTable;
	 
	 String aliasTable;

	public TableRef(int id, String nomTable, String aliasTable) {
		this.id = id;
		this.nomTable = nomTable;
		this.aliasTable = aliasTable;
	}
	
	public TableRef() {}

	public int getId() {
		return id; 
	}
 
	public void setId(int id) {
		this.id = id;
	}

	public String getNomTable() {
		return nomTable;
	}

	public void setNomTable(String nomTable) {
		this.nomTable = nomTable;
	}

	public String getAliasTable() {
		return aliasTable;
	}

	public void setAliasTable(String aliasTable) {
		this.aliasTable = aliasTable;
	}
	
	 
	 
	 
}
