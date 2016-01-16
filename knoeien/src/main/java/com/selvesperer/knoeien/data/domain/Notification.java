package com.selvesperer.knoeien.data.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "notification")
public class Notification extends AuditableEntity {

	private static final long serialVersionUID = -3145959798135200435L;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name ="description", length = 250)
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expire_date")
	@JsonIgnore
	private Calendar expireDate;
	
	public Notification() {
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
	
	public Calendar getExpireDate() {
		return expireDate;
	}
	
	public void setExpireDate(Calendar expireDate) {
		this.expireDate = expireDate;
	}
}
