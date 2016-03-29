package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.selvesperer.knoeien.data.domain.SliderImage;

public class SliderImageModel implements Serializable {

	private static final long serialVersionUID = 7515003367115860966L;
	
	private String id;
	
	private String imageUrl;
	
	public SliderImageModel() {}
	
	public SliderImageModel(SliderImage sliderImage) {
		this.setId(sliderImage.getId());
		this.setImageUrl(sliderImage.getImageUrl());
	}
	
	public List<SliderImageModel> getSliderImageModel (List<SliderImage> sliderImageList) {
		List<SliderImageModel> modelList = new ArrayList<>();
		for(SliderImage iterator : sliderImageList) {
			SliderImageModel sliderModel = new SliderImageModel(iterator);
			modelList.add(sliderModel);
		}
		return modelList;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


}
