package com.selvesperer.knoeien.data.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.selvesperer.knoeien.web.controllers.model.UserModel;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }, name = "UserEmailUK") })
public class User extends AuditableEntity {

	private static final long serialVersionUID = 1658852831685896178L;

	@Column(name = "company_id", length = 36)
	private String companyId;

	@Column(name = "is_admin", nullable = false, length = 1)
	private boolean admin = false;

	@Column(name = "first_name", length = 50)
	private String firstName;

	@Column(name = "middle_name", length = 50)
	private String middleName;

	@Column(name = "preferred_name", length = 50)
	private String preferredName;

	@Column(name = "last_name", length = 50)
	private String lastName;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "password", length = 256)
	private String password;

	@Column(name = "location", length = 100)
	private String location;
	
	@Column(name = "address", length = 512)
	private String address;
	
	@Column(name = "house_number", length = 50)
	private String houseNumber;

	@Column(name = "country_code", length = 3)
	private String countryCode;

	@Column(name = "phone", length = 10)
	private String phone;

	@Column(name = "active")
	private boolean active = true; // users will be assumed to be active by
									// default. Company activation controls
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "password_exp_date")
	private Calendar passwordExpirationDate;

	@Column(name = "password_reset_tkn")
	private String passwordResetToken;

	@NotNull
	@NotBlank
	@Column(name = "locale")
	private String locale = "en";

	@Column(name = "avatar_url", length = 300)
	private String avatarUrl = "";

	@Column(name = "background_image_url", length = 300)
	private String backgroundImageUrl;

	@Column(name = "rememberme_key", length = 250)
	private String remembermeKey;

	@Transient
	private String fullName;
	
	@Column(name ="date_of_birth")
	private Calendar dateOfBirth;
	
	@Column(name = "e_post")
	private boolean epost; 
	
	@Column(name = "sms")
	private boolean sms;
	
	@Column(name = "push_notification")
	private boolean pushNotification; 
	
	@Column(name = "receive_updates")
	private boolean receiveUpdates; 
	
	@Column(name = "receive_message")
	private boolean receiveMessage;
	
	@Column(name = "new_job_post")
	private boolean newJobPost; 
	
	@Column(name = "new_job_interest")
	private boolean newJobInterest;
	
	@Column(name = "assigned_job")
	private boolean assignedJob;
	
	@Column(name = "completed_job")
	private boolean completedJob;
	
	@Column(name = "postcode", length = 15)
	private String postcode;
	
	@Column(name = "about_me", length = 1024)
	private String aboutMe;
	
	public User() {}
	
	public User(UserModel userModel) {
		super();
		this.setFirstName(userModel.getFirstName());
		this.setLastName(userModel.getLastName());
		this.setPreferredName(userModel.getPreferredName());
		this.setEmail(userModel.getEmail());
		this.setPassword(userModel.getPassword());
		this.setPasswordResetToken(userModel.getPasswordResetToken());
		this.setDateOfBirth(userModel.getDateOfBirth());
		this.setPostcode(userModel.getPostcode());
		this.setAddress(userModel.getAddress());
		this.setHouseNumber(userModel.getHouseNumber());
		this.setAboutMe(userModel.getAboutMe());
	}
	
	public boolean isEpost() {
		return epost;
	}

	public void setEpost(boolean epost) {
		this.epost = epost;
	}

	public boolean isSms() {
		return sms;
	}

	public void setSms(boolean sms) {
		this.sms = sms;
	}

	public boolean isPushNotification() {
		return pushNotification;
	}

	public void setPushNotification(boolean pushNotification) {
		this.pushNotification = pushNotification;
	}

	public boolean isReceiveUpdates() {
		return receiveUpdates;
	}

	public void setReceiveUpdates(boolean receiveUpdates) {
		this.receiveUpdates = receiveUpdates;
	}

	public boolean isAssignedJob() {
		return assignedJob;
	}

	public void setAssignedJob(boolean assignedJob) {
		this.assignedJob = assignedJob;
	}

	public boolean isReceiveMessage() {
		return receiveMessage;
	}

	public void setReceiveMessage(boolean receiveMessage) {
		this.receiveMessage = receiveMessage;
	}

	public boolean isNewJobPost() {
		return newJobPost;
	}

	public void setNewJobPost(boolean newJobPost) {
		this.newJobPost = newJobPost;
	}

	public boolean isNewJobInterest() {
		return newJobInterest;
	}

	public void setNewJobInterest(boolean newJobInterest) {
		this.newJobInterest = newJobInterest;
	}
	
	public boolean isCompletedJob() {
		return completedJob;
	}

	public void setCompletedJob(boolean completedJob) {
		this.completedJob = completedJob;
	}
		
	public String getFullName() {					
		fullName = firstName + " " + lastName;
		return fullName;
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

	@JsonIgnore
	public String getObjectCode() {
		return "user";
	}
	
	@JsonIgnore
	public String salt() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(this.getCreatedDate()) + this.getId();
	}
	
	@Override
	@JsonIgnore
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		User other = (User) obj;
		if (getId() == null) {
			if (other.getId() != null) return false;
		} else if (!getId().equals(other.getId())) return false;
		return true;
	}
}
