package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.util.Calendar;

import com.selvesperer.knoeien.data.domain.User;

public class UserModel implements Serializable {

	private static final long serialVersionUID = 2361372848872111233L;

	private String companyId;

	private boolean admin = false;

	private String firstName;

	private String middleName;

	private String preferredName;

	private String lastName;

	private String email;

	private String password;
	
	private String location;
	
	private String address;
	
	private String houseNumber;

	private String countryCode;

	private String phone;

	private boolean active = true;

	private Calendar passwordExpirationDate;

	private String passwordResetToken;

	private String locale = "en";

	private String avatarUrl = "";

	private String backgroundImageUrl;

	private String remembermeKey;

	private String fullName;

	private String confirmPassword;
	
	private Calendar dateOfBirth;
	
	private boolean ePost;
	
	private boolean sms;
	
	private boolean message;
	
	private boolean reports;
	
	private boolean assignedJob;
	
	private boolean confirmJob;

	private boolean hideAddress;

	private boolean receiveUpdates;
	
	private String postcode;
	
	private String aboutMe;
	
	public UserModel() {}
	
	public UserModel(User user) {
		super();
		this.setEmail(user.getEmail());
		this.setPhone(user.getPhone());
		this.setFullName(user.getFullName());
		this.setLocation(user.getLocation());
		this.setCountryCode(user.getCountryCode());
		this.setePost(user.isePost());
		this.setSms(user.isSms());
		this.setMessage(user.isMessage());
		this.setReports(user.isReports());
		this.setAssignedJob(user.isAssignedJob());
		this.setConfirmJob(user.isConfirmJob());
		this.setHideAddress(user.isHideAddress());
		this.setReceiveUpdates(user.isReceiveUpdates());		
		this.setDateOfBirth(user.getDateOfBirth());
		this.setPostcode(user.getPostcode());
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setAddress(user.getAddress());
		this.setHouseNumber(user.getHouseNumber());
		this.setAboutMe(user.getAboutMe());
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Calendar getPasswordExpirationDate() {
		return passwordExpirationDate;
	}

	public void setPasswordExpirationDate(Calendar passwordExpirationDate) {
		this.passwordExpirationDate = passwordExpirationDate;
	}

	public String getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(String passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getBackgroundImageUrl() {
		return backgroundImageUrl;
	}

	public void setBackgroundImageUrl(String backgroundImageUrl) {
		this.backgroundImageUrl = backgroundImageUrl;
	}

	public String getRemembermeKey() {
		return remembermeKey;
	}

	public void setRemembermeKey(String remembermeKey) {
		this.remembermeKey = remembermeKey;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public boolean isePost() {
		return ePost;
	}

	public void setePost(boolean ePost) {
		this.ePost = ePost;
	}

	public boolean isSms() {
		return sms;
	}

	public void setSms(boolean sms) {
		this.sms = sms;
	}

	public boolean isMessage() {
		return message;
	}

	public void setMessage(boolean message) {
		this.message = message;
	}

	public boolean isReports() {
		return reports;
	}

	public void setReports(boolean reports) {
		this.reports = reports;
	}

	public boolean isAssignedJob() {
		return assignedJob;
	}

	public void setAssignedJob(boolean assignedJob) {
		this.assignedJob = assignedJob;
	}

	public boolean isConfirmJob() {
		return confirmJob;
	}

	public void setConfirmJob(boolean confirmJob) {
		this.confirmJob = confirmJob;
	}

	public boolean isHideAddress() {
		return hideAddress;
	}

	public void setHideAddress(boolean hideAddress) {
		this.hideAddress = hideAddress;
	}

	public boolean isReceiveUpdates() {
		return receiveUpdates;
	}

	public void setReceiveUpdates(boolean receiveUpdates) {
		this.receiveUpdates = receiveUpdates;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}	
}
