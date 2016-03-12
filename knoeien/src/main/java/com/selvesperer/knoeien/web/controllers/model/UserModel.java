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
	
	private String postcode;
	
	private String aboutMe;
	
	private boolean mobileSms;
	
	private boolean pushNotification;
	
	private boolean ePost; 
	
	private boolean receiveUpdates; 
	
	private boolean receiveMessage;
	
	private boolean jobPost;
	
	private boolean jobInterest;
	
	private boolean jobAssigned;
	
	private boolean completeJob;
	
	private String promoCode;
	
	private String cardNumber;
	
	private String cardholderName;
	
	private String accountNumber;
	
	private String cardMonth;
	
	private String cardYear;
	
	private String cvc;
	
	public UserModel() {}
	
	public UserModel(User user) {
		super();
		this.setEmail(user.getEmail());
		this.setPhone(user.getPhone());
		this.setFullName(user.getFullName());
		this.setLocation(user.getLocation());
		this.setCountryCode(user.getCountryCode());		
		this.setDateOfBirth(user.getDateOfBirth());
		this.setPostcode(user.getPostcode());
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setAddress(user.getAddress());
		this.setHouseNumber(user.getHouseNumber());
		this.setAboutMe(user.getAboutMe());
		this.setBackgroundImageUrl(user.getBackgroundImageUrl());
		this.setMobileSms(user.isMobileSms());
		this.setPushNotification(user.isPushNotification());
		this.setePost(user.isePost());
		this.setReceiveUpdates(user.isReceiveUpdates());
		this.setReceiveMessage(user.isReceiveMessage());
		this.setJobPost(user.isJobPost());
		this.setJobInterest(user.isJobInterest());
		this.setJobAssigned(user.isJobAssigned());
		this.setCompleteJob(user.isCompleteJob());		
		this.setPromoCode(user.getPromoCode());
		this.setCardNumber(user.getCardNumber());
		this.setCardholderName(user.getCardholderName());
		this.setAccountNumber(user.getAccountNumber());
		this.setCardMonth(user.getCardMonth());
		this.setCardYear(user.getCardYear());
		this.setCvc(user.getCvc());
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

	public boolean isMobileSms() {
		return mobileSms;
	}

	public void setMobileSms(boolean mobileSms) {
		this.mobileSms = mobileSms;
	}

	public boolean isPushNotification() {
		return pushNotification;
	}

	public void setPushNotification(boolean pushNotification) {
		this.pushNotification = pushNotification;
	}

	public boolean isePost() {
		return ePost;
	}

	public void setePost(boolean ePost) {
		this.ePost = ePost;
	}

	public boolean isReceiveUpdates() {
		return receiveUpdates;
	}

	public void setReceiveUpdates(boolean receiveUpdates) {
		this.receiveUpdates = receiveUpdates;
	}

	public boolean isReceiveMessage() {
		return receiveMessage;
	}

	public void setReceiveMessage(boolean receiveMessage) {
		this.receiveMessage = receiveMessage;
	}

	public boolean isJobPost() {
		return jobPost;
	}

	public void setJobPost(boolean jobPost) {
		this.jobPost = jobPost;
	}

	public boolean isJobInterest() {
		return jobInterest;
	}

	public void setJobInterest(boolean jobInterest) {
		this.jobInterest = jobInterest;
	}
	
	public boolean isJobAssigned() {
		return jobAssigned;
	}

	public void setJobAssigned(boolean jobAssigned) {
		this.jobAssigned = jobAssigned;
	}

	public boolean isCompleteJob() {
		return completeJob;
	}

	public void setCompleteJob(boolean completeJob) {
		this.completeJob = completeJob;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCardMonth() {
		return cardMonth;
	}

	public void setCardMonth(String cardMonth) {
		this.cardMonth = cardMonth;
	}

	public String getCardYear() {
		return cardYear;
	}

	public void setCardYear(String cardYear) {
		this.cardYear = cardYear;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

		
}
