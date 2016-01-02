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

	@Column(name = "url", length = 250)
	private String url;

	@Column(name = "company_category", length = 250)
	private String companyCategory;

	@Column(name = "company_sub_category", length = 150)
	private String companySubCategory;

	@Column(name = "city", length = 150)
	private String city;

	@Column(name = "state", length = 100)
	private String state;

	@Column(name = "country", length = 3)
	private String country;

	@Column(name = "gps_location", length = 200)
	private String gpsLocation;

	@Column(name = "origin", length = 100)
	private String origin;

	@Column(name = "is_verified")
	private boolean isVerified = false;

	@Column(name = "is_active")
	private boolean isActive = false;

	@Column(name = "is_live")
	private boolean isLive = false;

	@Column(name = "license_count")
	private int licenseCount = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "account_expiration")
	@JsonIgnore
	private Calendar accountExpirationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "go_live_date")
	@JsonIgnore
	private Calendar goLiveDate;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "cost_per_license")
	private BigDecimal costPerLicense = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

	@Column(name = "admin_id", length = 36)
	private String adminId;

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

	@JsonIgnore
	public String getObjCode() {
		return "cmpy";
	}

	@Override
	public String getCompanyID() {
		return null;
	}
}
