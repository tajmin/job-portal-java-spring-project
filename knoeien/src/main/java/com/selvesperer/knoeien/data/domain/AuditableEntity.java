package com.selvesperer.knoeien.data.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.selvesperer.knoeien.utils.SelvEDate;

@MappedSuperclass
@EntityListeners(value = { AuditEntityListener.class })
public abstract class AuditableEntity extends PersistableEntity implements HasCreatedById {
	
	private static final long serialVersionUID = -4504547440588298187L;

	@Column(name = "created_by_name", length = 150)
	private String createdByName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Calendar createdDate;

	@Column(name = "last_modified_by_name", length = 150)
	private String lastModifiedByName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified_date")
	private Calendar lastModifiedDate;

	@Column(name = "created_by_id", length = 36)
	private String createdByID;

	@Column(name = "last_modified_by_id", length = 36)
	private String lastModifiedByID;

	public SelvEDate getCreatedDate() {
		return SelvEDate.nullableDate(createdDate);
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public SelvEDate getLastModifiedDate() {
		return SelvEDate.nullableDate(lastModifiedDate);
	}

	public void setLastModifiedDate(Calendar lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(final String createdBy) {
		this.createdByName = createdBy;
	}

	public String getLastModifiedByName() {
		return lastModifiedByName;
	}

	public void setLastModifiedByName(final String lastModifiedBy) {
		this.lastModifiedByName = lastModifiedBy;
	}

	public String getCreatedByID() {
		return createdByID;
	}

	public void setCreatedByID(String createdByID) {
		this.createdByID = createdByID;
	}

	public String getLastModifiedByID() {
		return lastModifiedByID;
	}

	public void setLastModifiedByID(String lastModifiedByID) {
		this.lastModifiedByID = lastModifiedByID;
	}
	
	public String getReadableCreatedDate() {
		if (this.getCreatedDate() != null) {
			return SelvEDate.nullableDate(createdDate).toReadableString();
		}
		return "";
	}
	
	public String getReadableDate(Calendar date) {
		if (this.getCreatedDate() != null) {
			return SelvEDate.nullableDate(date).toReadableString();
		}
		return "";
	}
	

	
}
