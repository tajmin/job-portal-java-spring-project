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
	
	//@author SHIFAT member variable for settings.xhtml
	
	@Column(name = "e_post")
	private boolean ePost; 
	
	@Column(name = "sms")
	private boolean sms;
	
	@Column(name = "message")
	private boolean message; 
	
	@Column(name = "reports")
	private boolean reports; 
	
	@Column(name = "assigned_job")
	private boolean assignedJob;
	
	@Column(name = "confirm_job")
	private boolean confirmJob; 
	
	@Column(name = "hide_address")
	private boolean hideAddress;
	
	@Column(name = "receive_updates")
	private boolean receiveUpdates;
	
	//@author SHIFAT ends here
	
	
	
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
		
	}
	
	//@author SHIFAT edited for setting 

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
	
	
	//settings ends
	
	
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
