package com.sofct.sofct.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Configuration {
	
      @Id
      @GeneratedValue
	  private int id;
      
	  private String nomTable;
	  private String aliasTable;
	  private String nomColonne;
	  private String aliasColonne;
	  private String type;
	   
	  public Configuration() {}
	  
	  
	public Configuration(int id, String nomTable, String aliasTable, String nomColonne, String aliasColonne,String type) {
		this.id = id;
		this.nomTable = nomTable;
		this.aliasTable = aliasTable;
		this.nomColonne = nomColonne; 
		this.aliasColonne = aliasColonne;
		this.type = type;
	}


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


	public String getNomColonne() {
		return nomColonne;
	}


	public void setNomColonne(String nomColonne) {
		this.nomColonne = nomColonne;
	}


	public String getAliasColonne() {
		return aliasColonne;
	}


	public void setAliasColonne(String aliasColonne) {
		this.aliasColonne = aliasColonne;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	} 
	  
	 

} 
