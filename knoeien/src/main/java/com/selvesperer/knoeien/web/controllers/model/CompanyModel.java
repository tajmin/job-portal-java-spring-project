package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

public class CompanyModel implements Serializable {

	private static final long serialVersionUID = -4601977999718229676L;
	
	private String name;

	private String description;
	
	private String contactNumber;
	
	private String email;
	
	private String url;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String addressLine3;
	
	private String zip;
		
	private String city;
	
	private String state;

	private String country;
	
	private String facebook;
	
	private String googlePlus;
	
	private String linkedin;
	
	private BigDecimal latitude = BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP);
	
	private BigDecimal longitude = BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP);

	private Calendar recentJobPeriod;
		
	private BigDecimal bestJobAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

	private Calendar shortestTimeJobPeriod;
	
	private Calendar earliestDateJobPeriod;
	
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

}
