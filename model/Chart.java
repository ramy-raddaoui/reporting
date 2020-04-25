package com.sofct.sofct.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

@Entity
public class Chart {
	
	 @Id
     @GeneratedValue
	  private int id;
	 
	 private String reportName;
	 private String reportDesc;
	 private String gReport;
	 private String abscisse;
	 
	 private String displayType;
	 
	    @ManyToMany
	    Set<DayNumber> dayNumbers;
	     
	    @ManyToMany
	    Set<WeekDays> weekDays;
	    
		@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "chart")
		private List<SpecificMail> specificMails;
		
		@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "chart")
		private List<GroupBy> GroupByItems;
		
		@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "chart")
		private List<Ordonnée> OrdonnéeItems;
		
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "chart")
		private List<Condition> conditions;
		

	    
	 @ManyToMany 
	 private List<User> recipients;
	 
	 @ManyToMany   
	 private List<User> excepts;
	  
		@ManyToOne
		private User proprietaire;
		
		@ManyToOne
		private TableRef tableReferenced;

	 private String onSpecificDate; 
	 
	 public Chart() {}


	public Chart(int id, String reportName, String reportDesc, String gReport, String abscisse, String displayType,
			Set<DayNumber> dayNumbers, Set<WeekDays> weekDays, List<SpecificMail> specificMails,
			List<GroupBy> groupByItems, List<Ordonnée> ordonnéeItems, List<Condition> conditions,
			List<User> recipients, List<User> excepts, User proprietaire, TableRef tableReferenced,
			String onSpecificDate) {
		super();
		this.id = id;
		this.reportName = reportName;
		this.reportDesc = reportDesc;
		this.gReport = gReport;
		this.abscisse = abscisse;
		this.displayType = displayType;
		this.dayNumbers = dayNumbers;
		this.weekDays = weekDays;
		this.specificMails = specificMails;
		GroupByItems = groupByItems;
		OrdonnéeItems = ordonnéeItems;
		this.conditions = conditions;
		this.recipients = recipients;
		this.excepts = excepts;
		this.proprietaire = proprietaire;
		this.tableReferenced = tableReferenced;
		this.onSpecificDate = onSpecificDate;
	}






	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public String getReportName() {
		return reportName;
	}




	public void setReportName(String reportName) {
		this.reportName = reportName;
	}





	public String getReportDesc() {
		return reportDesc;
	}






	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}




	public String getgReport() {
		return gReport;
	}



	public void setgReport(String gReport) {
		this.gReport = gReport;
	}






	public String getAbscisse() {
		return abscisse;
	}




	public void setAbscisse(String abscisse) {
		this.abscisse = abscisse;
	}



	public String getdisplayType() {
		return displayType;
	}

	
	public void setdisplayType(String displayType) {
		this.displayType = displayType;
	}



	public Set<DayNumber> getDayNumbers() {
		return dayNumbers;
	}


	public void setDayNumbers(Set<DayNumber> dayNumbers) {
		this.dayNumbers = dayNumbers;
	}


	public Set<WeekDays> getWeekDays() {
		return weekDays;
	}



	public void setWeekDays(Set<WeekDays> weekDays) {
		this.weekDays = weekDays;
	}



	public List<SpecificMail> getSpecificMails() {
		return specificMails;
	}


	public void setSpecificMails(List<SpecificMail> specificMails) {
		this.specificMails = specificMails;
	}





	public List<GroupBy> getGroupByItems() {
		return GroupByItems;
	}





	public void setGroupByItems(List<GroupBy> groupByItems) {
		GroupByItems = groupByItems;
	}



	public List<Ordonnée> getOrdonnéeItems() {
		return OrdonnéeItems;
	}



	public void setOrdonnéeItems(List<Ordonnée> ordonnéeItems) {
		OrdonnéeItems = ordonnéeItems;
	}



	public List<Condition> getConditions() {
		return conditions;
	}




	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}



	public List<User> getRecipients() {
		return recipients;
	}




	public void setRecipients(List<User> recipients) {
		this.recipients = recipients;
	}




	public List<User> getExcepts() {
		return excepts;
	}



	public void setExcepts(List<User> excepts) {
		this.excepts = excepts;
	}




	public User getProprietaire() {
		return proprietaire;
	}




	public void setProprietaire(User proprietaire) {
		this.proprietaire = proprietaire;
	}



	public TableRef getTableReferenced() {
		return tableReferenced;
	}



	public void setTableReferenced(TableRef tableReferenced) {
		this.tableReferenced = tableReferenced;
	}


	public String getOnSpecificDate() {
		return onSpecificDate;
	}


	public void setOnSpecificDate(String onSpecificDate) {
		this.onSpecificDate = onSpecificDate;
	}


	public void addSpecificMails(SpecificMail specificMails) {
		this.specificMails.add(specificMails);
	}
	

 

	 
}
