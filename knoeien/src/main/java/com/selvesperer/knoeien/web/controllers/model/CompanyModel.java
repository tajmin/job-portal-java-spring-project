package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

public class CompanyModel implements Serializable {

	private static final long serialVersionUID = -4601977999718229676L;
	
	private String name;
	
	private String description;
	
	private String url;
	
	private String companyCategory;
	
	private String companySubCategory;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String gpsLocation;
	
	private String origin;
	
	private boolean isVerified = false;
	
	private boolean isActive = false;
	
	private boolean isLive = false;
	
	private int licenseCount = 0;
	
	private Calendar accountExpirationDate;
	
	private Calendar goLiveDate;
	
	private String email;
	
	private BigDecimal costPerLicense = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	private String adminId;
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCompanyCategory() {
		return companyCategory;
	}

	public void setCompanyCategory(String companyCategory) {
		this.companyCategory = companyCategory;
	}

	public String getCompanySubCategory() {
		return companySubCategory;
	}

	public void setCompanySubCategory(String companySubCategory) {
		this.companySubCategory = companySubCategory;
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

	public String getGpsLocation() {
		return gpsLocation;
	}

	public void setGpsLocation(String gpsLocation) {
		this.gpsLocation = gpsLocation;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public int getLicenseCount() {
		return licenseCount;
	}

	public void setLicenseCount(int licenseCount) {
		this.licenseCount = licenseCount;
	}

	public Calendar getAccountExpirationDate() {
		return accountExpirationDate;
	}

	public void setAccountExpirationDate(Calendar accountExpirationDate) {
		this.accountExpirationDate = accountExpirationDate;
	}

	public Calendar getGoLiveDate() {
		return goLiveDate;
	}

	public void setGoLiveDate(Calendar goLiveDate) {
		this.goLiveDate = goLiveDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getCostPerLicense() {
		return costPerLicense;
	}

	public void setCostPerLicense(BigDecimal costPerLicense) {
		this.costPerLicense = costPerLicense;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

}
