package com.selvesperer.knoeien.data.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.selvesperer.knoeien.utils.AppsUtil;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

/**
 * Company Object to store the company data of a user.
 * 
 * @author Mithun <shahinur.bd@gmail.com>
 */
@Entity
@Table(name = "job")
public class Job extends AuditableEntity {

	private static final long serialVersionUID = 8120068076551599301L;
	
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
	
	@Column(name = "title", nullable = true, length = 100)
	private String title;

	@Column(name = "description", nullable = false, length = 250)
	private String description;
	
	@Column(name = "draft")
	private boolean draft;
	
	@Column(name = "image_url", length = 300)
	private String imageUrl;

	@Column(name = "latitude", length = 30)
	private String latitude;

	@Column(name = "longitude", length = 30)
	private String longitude;
	
	@Column(name = "assigned_user_id", length = 50)
	private String assignedUserId;

	@Column(name = "rating")
	private Integer rating;

	@Column(name = "review_message", length = 50)
	private String reviewMessage;
	
	@Column(name = "sales_promo_code", length = 50)
	private String salesPromoCode;
	
	@Column(name = "deadline_month")
	private Integer deadlineMonth;
	
	@Column(name = "deadline_day")
	private Integer deadlineDay;
	
	@Column(name = "deadline_time", length = 50)
	private String deadlineTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deadline")
	@JsonIgnore
	private Calendar deadline;
	
	@Column(name = "hours")
	private Integer hours;
	
	@Column(name = "minutes")
	private Integer minutes;
	
	@Column(name = "seconds")
	private Integer seconds;

	@Column(name = "duration")
	private Integer duration;

	@Column(name = "price")
	private Double price;

	@Column(name = "percent")
	private Double percent;
	
	@Column(name = "total_price")
	private Double totalPrice;

	public Job() {}

	public Job(JobModel jobModel) {
		super();
		this.setId(jobModel.getId());
		this.setAddressLine1(jobModel.getAddressLine1());
		this.setAddressLine2(jobModel.getAddressLine2());
		this.setAddressLine3(jobModel.getAddressLine3());
		this.setCity(jobModel.getCity());
		this.setState(jobModel.getState());
		this.setZip(jobModel.getZip());
		this.setTitle(jobModel.getTitle());
		this.setDescription(jobModel.getDescription());
		this.setDraft(jobModel.isDraft());
		this.setImageUrl(jobModel.getImageUrl());
		this.setLatitude(jobModel.getLatitude());
		this.setLongitude(jobModel.getLongitude());
		this.setAssignedUserId(jobModel.getAssignedUserId());
		this.setRating(jobModel.getRating());
		this.setReviewMessage(jobModel.getReviewMessage());
		this.setSalesPromoCode(jobModel.getSalesPromoCode());
		
		this.setDeadlineMonth(jobModel.getDeadlineMonth());
		this.setDeadlineDay(jobModel.getDeadlineDay());
		this.setDeadlineTime(jobModel.getDeadlineTime());
		this.setDeadline(AppsUtil.getCalenderByAdding(jobModel.getDeadlineMonth(), jobModel.getDeadlineDay(), null));
		
		this.setHours(jobModel.getHours());
		this.setMinutes(jobModel.getMinutes());
		this.setSeconds(jobModel.getSeconds());
		this.setDuration(AppsUtil.getDurationInSecond(jobModel.getHours(), jobModel.getMinutes(), 0));
		
		this.setPrice(AppsUtil.stringToDouble(jobModel.getPrice()));
		this.setPercent(AppsUtil.stringToDouble(jobModel.getPercent()));
		this.setTotalPrice(AppsUtil.addCommision(this.getPrice(), this.getPercent()));
	}

	public String getAssignedUserId() {
		return assignedUserId;
	}

	public void setAssignedUserId(String assignedUserId) {
		this.assignedUserId = assignedUserId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getReviewMessage() {
		return reviewMessage;
	}

	public void setReviewMessage(String reviewMessage) {
		this.reviewMessage = reviewMessage;
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

	public String getSalesPromoCode() {
		return salesPromoCode;
	}

	public void setSalesPromoCode(String salesPromoCode) {
		this.salesPromoCode = salesPromoCode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Calendar getDeadline() {
		return deadline;
	}

	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getDeadlineMonth() {
		return deadlineMonth;
	}

	public void setDeadlineMonth(Integer deadlineMonth) {
		this.deadlineMonth = deadlineMonth;
	}

	public Integer getDeadlineDay() {
		return deadlineDay;
	}

	public void setDeadlineDay(Integer deadlineDay) {
		this.deadlineDay = deadlineDay;
	}

	public String getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(String deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
	
}
