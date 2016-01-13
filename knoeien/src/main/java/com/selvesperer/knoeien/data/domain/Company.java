package com.selvesperer.knoeien.data.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.selvesperer.knoeien.web.controllers.model.CompanyModel;

/**
 * Company Object to store the company data of a user.
 * 
 * @author Mithun <shahinur.bd@gmail.com>
 */
@Entity
@Table(name = "company")
public class Company extends AuditableEntity {

	private static final long serialVersionUID = 817782032520736141L;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "description", length = 250)
	private String description;
	
	@Column(name = "contact_number", length = 100)
	private String contactNumber;
	
	@Column(name = "email", length = 100)
	private String email;
	
	@Column(name = "url", length = 250)
	private String url;
	
	@Column(name = "address_line_1", length = 100)
	private String addressLine1;
	
	@Column(name = "address_line_2", length = 100)
	private String addressLine2;
	
	@Column(name = "address_line_3", length = 100)
	private String addressLine3;
	
	@Column(name = "zip", length = 10)
	private String zip;
	
	@Column(name = "city", length = 100)
	private String city;

	@Column(name = "state", length = 100)
	private String state;

	@Column(name = "country", length = 3)
	private String country;
	
	@Column(name = "facebook", length = 100)
	private String facebook;
	
	@Column(name = "googleplus", length = 100)
	private String googlePlus;
	
	@Column(name = "linkedin", length = 100)
	private String linkedin;
	
	@Column(name = "latitude", nullable = false)
	private BigDecimal latitude = BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP);
	
	@Column(name = "longitude", nullable = false)
	private BigDecimal longitude = BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP);

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "recent_job_period")
	@JsonIgnore
	private Calendar recentJobPeriod;
	
	@Column(name = "best_job_amount", nullable = false)
	private BigDecimal bestJobAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "shortest_time_job_period")
	@JsonIgnore
	private Calendar shortestTimeJobPeriod;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "earliest_date_job_period")
	@JsonIgnore
	private Calendar earliestDateJobPeriod;

	
	
	public Company() {
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
	
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	
	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	
	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	
	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getGooglePlus() {
		return googlePlus;
	}

	public void setGooglePlus(String googlePlus) {
		this.googlePlus = googlePlus;
	}
	
	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public Calendar getRecentJobPeriod() {
		return recentJobPeriod;
	}

	public void setRecentJobPeriod(Calendar recentJobPeriod) {
		this.recentJobPeriod = recentJobPeriod;
	}

	public BigDecimal getBestJobAmount() {
		return bestJobAmount;
	}

	public void setBestJobAmount(BigDecimal bestJobAmount) {
		this.bestJobAmount = bestJobAmount;
	}
	
	public Calendar getShortestTimeJobPeriod() {
		return shortestTimeJobPeriod;
	}

	public void setShortestTimeJobPeriod(Calendar shortestTimeJobPeriod) {
		this.shortestTimeJobPeriod = shortestTimeJobPeriod;
	}
	
	public Calendar getEarliestDateJobPeriod() {
		return earliestDateJobPeriod;
	}

	public void setEarliestDateJobPeriod(Calendar earliestDateJobPeriod) {
		this.earliestDateJobPeriod = earliestDateJobPeriod;
	}


	@JsonIgnore
	public String getObjCode() {
		return "cmpy";
	}

	@Override
	public String getCompanyID() {
		return null;
	}
}
