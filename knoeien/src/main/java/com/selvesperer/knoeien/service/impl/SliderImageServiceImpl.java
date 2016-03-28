package com.selvesperer.knoeien.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.SliderImage;
import com.selvesperer.knoeien.data.repository.SliderImageRepository;
import com.selvesperer.knoeien.service.SliderImageService;
import com.selvesperer.knoeien.spring.ScopeType;
import com.selvesperer.knoeien.web.controllers.model.SliderImageModel;

@Service("sliderImageService")
@Scope(ScopeType.SINGLETON)
public class SliderImageServiceImpl implements SliderImageService {
	
	@Inject
	private SliderImageRepository sliderImageRepo;

	@Override
	public List<SliderImage> findSliderImages() {
		List <SliderImage> sliderImageList = sliderImageRepo.findSliderImageList();
		return sliderImageList;
	}

	@Override
	public void saveImage(SliderImageModel sliderImageModel) {
		SliderImage sliderImage = new SliderImage(sliderImageModel);
		sliderImageRepo.saveAndFlush(sliderImage);
	}

}
