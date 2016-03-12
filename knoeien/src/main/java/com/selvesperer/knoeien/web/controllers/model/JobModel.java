package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.utils.AppsUtil;
import com.selvesperer.knoeien.utils.DateFormatUtils;

public class JobModel implements Serializable{

	private static final long serialVersionUID = 1348771858514768261L;
	
	private String id;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String state;
	private String zip;
	private String title;
	private String description;
	private boolean draft;
	private String imageUrl;
	private String latitude;
	private String longitude;
	private String assignedUserId;
	private Integer rating;
	private String reviewMessage;
	private String salesPromoCode;
	
	private int deadlineMonth;
	private int deadlineDay;
	private String deadlineTime;
	private String deadline;
	
	private int hours;
	private int minutes;
	private int seconds;
	private int duration;
	
	
	private String price;
	private String percent = "-10";
	private String totalPrice;
	
	private String whenPosted;
	
	public JobModel() {}
	
	public JobModel(Job job) {
		this.setId(job.getId());
		this.setAddressLine1(job.getAddressLine1());
		this.setAddressLine2(job.getAddressLine2());
		this.setAddressLine3(job.getAddressLine3());
		this.setCity(job.getCity());
		this.setState(job.getState());
		this.setZip(job.getZip());
		this.setTitle(job.getTitle());
		this.setDescription(job.getDescription());
		this.setDraft(job.isDraft());
		this.setImageUrl(job.getImageUrl());
		this.setLatitude(job.getLatitude());
		this.setLongitude(job.getLongitude());
		this.setAssignedUserId(job.getAssignedUserId());
		this.setRating(job.getRating());
		this.setReviewMessage(job.getReviewMessage());
		this.setSalesPromoCode(job.getSalesPromoCode());
		
		this.setDeadlineMonth(job.getDeadlineMonth());
		this.setDeadlineDay(job.getDeadlineDay());
		this.setDeadlineTime(job.getDeadlineTime());
		this.setDeadline(DateFormatUtils.getWebFormattedDateString(job.getDeadline()));
		
		this.setHours(job.getHours());
		this.setMinutes(job.getMinutes());
		this.setSeconds(job.getSeconds());
		this.setDuration(job.getDuration());
		
		this.setPrice(AppsUtil.doubleToString(job.getPrice()));
		this.setPercent(AppsUtil.doubleToString(job.getPercent()));
		this.setTotalPrice(AppsUtil.doubleToString(job.getTotalPrice()));
		
		this.setWhenPosted(AppsUtil.getDiffenrence(job.getCreatedDate()));
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<JobModel> getJobModelList(List<Job> jobList) {
		List<JobModel> jobModelList = new ArrayList<>();
		for(Job job : jobList) {
			JobModel jobModel = new JobModel(job);
			jobModelList.add(jobModel);
		}
		return jobModelList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSalesPromoCode() {
		return salesPromoCode;
	}

	public void setSalesPromoCode(String salesPromoCode) {
		this.salesPromoCode = salesPromoCode;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
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

	public int getDeadlineMonth() {
		return deadlineMonth;
	}

	public void setDeadlineMonth(int deadlineMonth) {
		this.deadlineMonth = deadlineMonth;
	}

	public int getDeadlineDay() {
		return deadlineDay;
	}

	public void setDeadlineDay(int deadlineDay) {
		this.deadlineDay = deadlineDay;
	}

	public String getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(String deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public String getWhenPosted() {
		return whenPosted;
	}

	public void setWhenPosted(String whenPosted) {
		this.whenPosted = whenPosted;
	}
}
