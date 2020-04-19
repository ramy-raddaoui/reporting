package com.sofct.sofct.types;

public class getAliasTableType {

	String aliasTable;
	String nomTable;
	public getAliasTableType(String aliasTable, String nomTable) {
		this.aliasTable = aliasTable;
		this.nomTable = nomTable;
	}
	public getAliasTableType() {}
	
	
	public String getAliasTable() {
		return aliasTable;
	}
	public void setAliasTable(String aliasTable) {
		this.aliasTable = aliasTable;
	}
	public String getNomTable() {
		return nomTable;
	}
	public void setNomTable(String nomTable) {
		this.nomTable = nomTable;
	}
	
	
}
