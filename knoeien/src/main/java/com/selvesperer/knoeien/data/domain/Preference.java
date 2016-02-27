package com.selvesperer.knoeien.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="preference")
public class Preference extends AuditableEntity {
	
	private static final long serialVersionUID = -5245298603385153044L;

	@Column(name="epost")
	private Boolean epost;
	
	@Column(name="sms")
	private Boolean sms;
	
	@Column(name="send_me_message")
	private Boolean sendMeMessage;
	
	@Column(name="interested_in_my_job")
	private Boolean interestedInMyJob;
	
	@Column(name="assigned_job")
	private Boolean assignedJob;
	
	@Column(name="done_job")
	private Boolean doneJob;
	
	@Column(name="job_posted_in_my_area")
	private Boolean jobPostedInMyArea;
	
	@Column(name="hide_my_address")
	private Boolean hideMyAddress;
	
	@Column(name="receive_email_update")
	private Boolean receiveEmailUpdate;
	
	@Column(name="email_invitation_to_friend")
	private Boolean emailInvitationToFriend;
	
	public Preference() {
		super();
	}
	
	public Boolean getEpost() {
		return epost;
	}
	
	public void setEpost(Boolean epost) {
		this.epost = epost;
	}
	
	public Boolean getSms() {
		return sms;
	}
	
	public void setSms(Boolean sms) {
		this.sms = sms;
	}
	
	public Boolean getSendMeMessage() {
		return sendMeMessage;
	}
	
	public void setSendMeMessage(Boolean sendMeMessage) {
		this.sendMeMessage = sendMeMessage;
	}
	
	public Boolean getInterestedInMyJob() {
		return interestedInMyJob;
	}
	
	public void setInterestedInMyJob(Boolean interestedInMyJob) {
		this.interestedInMyJob = interestedInMyJob;
	}
	
	public Boolean getAssignedJob() {
		return assignedJob;
	}
	
	public void setAssignedJob(Boolean assignedJob) {
		this.assignedJob = assignedJob;
	}
	
	public Boolean getDoneJob() {
		return doneJob;
	}
	
	public void setDonetJob(Boolean doneJob) {
		this.doneJob = doneJob;
	}
	
	public Boolean getJobPostedInMyArea() {
		return jobPostedInMyArea;
	}
	
	public void setJobPostedInMyArea(Boolean jobPostedInMyArea) {
		this.jobPostedInMyArea = jobPostedInMyArea;
	}
	
	public Boolean getHideMyAddress() {
		return hideMyAddress;
	}
	
	public void setHideMyAddress(Boolean hideMyAddress) {
		this.hideMyAddress = hideMyAddress;
	}
	
	public Boolean getReceiveEmailUpdate() {
		return receiveEmailUpdate;
	}
	
	public void setReceiveEmailUpdate(Boolean receiveEmailUpdate) {
		this.receiveEmailUpdate = receiveEmailUpdate;
	}
	
	public Boolean getEmailInvitationToFriend() {
		return emailInvitationToFriend;
	}
	
	public void setEmailInvitationToFriend(Boolean emailInvitationToFriend) {
		this.emailInvitationToFriend = emailInvitationToFriend;
	}
}
