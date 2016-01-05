package com.selvesperer.knoeien.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "job_interested")
public class JobInterested extends AuditableEntity {

	private static final long serialVersionUID = 5454522042314035295L;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name ="description", length = 250)
	private String description;
	
	public JobInterested() {
		super();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getObjCode() {
		// TODO Auto-generated method stub
		return "jbin";
	}

	@Override
	public String getCompanyID() {
		// TODO Auto-generated method stub
		return null;
	}

}
