package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.utils.DateFormatUtils;

public class JobModel implements Serializable{

	private static final long serialVersionUID = 1348771858514768261L;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String addressLine3;
	
	private String city;
	
	private String state;
	
	private String zip;
	
	private String title;
	
	private String description;
	
	private String date;
	
	private Integer duration;
	
	private BigDecimal payment = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	public JobModel() {}
	
	public JobModel(Job job) {		
		this.setTitle(job.getTitle());
		this.setDescription(job.getDescription());
		this.setAddressLine1(job.getAddressLine1());
		this.setAddressLine2(job.getAddressLine2());
		this.setAddressLine3(job.getAddressLine3());
		this.setCity(job.getCity());
		this.setDuration(job.getDuration());
		this.setPayment(job.getPayment());
		this.setDate(DateFormatUtils.getDBFormattedDateString(job.getDate()));
		this.setZip(job.getZip());
		this.setState(job.getState());
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

}
