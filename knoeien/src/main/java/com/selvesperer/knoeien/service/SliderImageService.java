package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.data.domain.SliderImage;
import com.selvesperer.knoeien.web.controllers.model.SliderImageModel;

public interface SliderImageService {
	
	public List<SliderImage> findSliderImages();
	
	public void saveImage(SliderImageModel sliderImageModel);

}
