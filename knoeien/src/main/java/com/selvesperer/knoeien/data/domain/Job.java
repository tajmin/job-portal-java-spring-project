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
import com.selvesperer.knoeien.utils.DateFormatUtils;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

/**
 * Company Object to store the company data of a user.
 * 
 * @author Mithun <shahinur.bd@gmail.com>
 */
@Entity
@Table(name="job")
public class Job extends AuditableEntity {

	private static final long serialVersionUID = 8120068076551599301L;

	@Column(name = "title", nullable = true, length = 100)
	private String title;
	
	@Column(name = "description", nullable = false, length = 250)
	private String description;
	
	@Column(name = "duration", nullable = false)
	private Integer duration;
	
	@Column(name = "payment", nullable = false)
	private BigDecimal payment = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	@JsonIgnore
	private Calendar date;
	
	@Column(name = "image_url", length = 300)
	private String imageUrl;
	
	@Column(name = "address_line_1", length = 100)
	private String addressLine1;
	
	@Column(name = "address_line_2", length = 100)
	private String addressLine2;
	
	@Column(name = "address_line_3", length = 100)
	private String addressLine3;
	
	@Column(name = "city", length = 100)
	private String city;
	
	@Column(name = "state", length = 100)
	private String state;
	
	@Column(name = "zip", length = 10)
	private String zip;
	
	@Column(name="draft")
	private boolean draft;
	
	@Column(name = "latitude", nullable = false)
	private BigDecimal latitude = BigDecimal.ZERO.setScale(6, RoundingMode.HALF_UP);
	
	@Column(name = "longitude", nullable = false)
	private BigDecimal longitude = BigDecimal.ZERO.setScale(6, RoundingMode.HALF_UP);
	
	public Job() {}
	
	public Job(JobModel jobModel) {
		super();
		this.setTitle(jobModel.getTitle());
		this.setDescription(jobModel.getDescription());
		this.setDuration(jobModel.getDuration());
		this.setPayment(jobModel.getPayment());
		this.setDate(DateFormatUtils.getDBCalendarFromString(jobModel.getDate()));
		this.setAddressLine1(jobModel.getAddressLine1());
		this.setAddressLine2(jobModel.getAddressLine2());
		this.setAddressLine3(jobModel.getAddressLine3());
		this.setCity(jobModel.getCity());
		this.setState(jobModel.getState());
		this.setZip(jobModel.getZip());
		this.setDraft(jobModel.isDraft());
		this.setImageUrl(jobModel.getImageUrl());
		this.setLatitude(jobModel.getLatitude());
		this.setLongitude(jobModel.getLongitude());
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getDuration() {
		return duration;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}
	
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	public boolean isDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
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
}
