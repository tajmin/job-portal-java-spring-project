package com.selvesperer.knoeien.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.selvesperer.knoeien.web.controllers.model.SliderImageModel;

/**
 * Slide object for storing
 * homepage slider images
 */

@Entity
@Table(name = "slider_image")
public class SliderImage extends AuditableEntity {

	private static final long serialVersionUID = -8464926135555320677L;
	
	@Column(name = "image_url", length = 300)
	private String imageUrl;
	
	public SliderImage() {}
	
	public SliderImage(SliderImageModel sliderImageModel) {
		super();
		this.setImageUrl(sliderImageModel.getImageUrl());
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	};

}
