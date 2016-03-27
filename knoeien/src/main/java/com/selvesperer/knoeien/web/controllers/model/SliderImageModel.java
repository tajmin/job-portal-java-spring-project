package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;

import com.selvesperer.knoeien.data.domain.SliderImage;

public class SliderImageModel implements Serializable {

	private static final long serialVersionUID = 7515003367115860966L;
	
	private String imageUrl;
	
	public SliderImageModel() {}
	
	public SliderImageModel(SliderImage sliderImage) {
		this.setImageUrl(sliderImage.getImageUrl());
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	};
	
	

}
