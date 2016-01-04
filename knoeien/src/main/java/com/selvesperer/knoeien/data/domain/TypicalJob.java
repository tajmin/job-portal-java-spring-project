package com.selvesperer.knoeien.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Company Object to store the company data of a user.
 * 
 * @author Mithun <shahinur.bd@gmail.com>
 */
@Entity
@Table(name="typical_job")
public class TypicalJob extends AuditableEntity{
	
	private static final long serialVersionUID = 8910243905579440319L;

	@Column(name="name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "title", nullable = false, length = 100)
	private String title;
	
	@Column(name = "description", nullable = false, length = 250)
	private String description;
	
	@Column(name = "image_url", length = 100)
	private String imageUrl;
	
	public TypicalJob() {
		super();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String getObjCode() {
		return "tpjb";
	}

	@Override
	public String getCompanyID() {
		// TODO Auto-generated method stub
		return null;
	}

}
