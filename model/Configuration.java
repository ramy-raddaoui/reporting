package com.sofct.sofct.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Configuration {
	
      @Id
      @GeneratedValue
	  private int id;
      
		@ManyToOne
		private TableRef tableReferenced;
		
		

	  private String nomColonne;
	  private String aliasColonne;
	  private String type;
	   
	  public Configuration() {}
	  
	  


	public Configuration(int id, TableRef tableReferenced, String nomTable, String aliasTable, String nomColonne,
			String aliasColonne, String type) {
		super();
		this.id = id;
		this.tableReferenced = tableReferenced;
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




	public TableRef getTable() {
		return tableReferenced;
	}




	public void setTable(TableRef tableReferenced) {
		this.tableReferenced = tableReferenced;
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
