package com.selvesperer.knoeien.web.controllers.rest;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.selvesperer.knoeien.data.domain.SliderImage;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.service.SliderImageService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.IdGenerator;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.SliderImageModel;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;


@Controller
@RequestMapping(value = "/api/v1/sliderimage")
public class SliderImageController extends AbstractController implements Serializable {

	private static final long serialVersionUID = -4227174072568002040L;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Integer getData() {
		return 1;
	}
	
	@RequestMapping(value = "/uploadimage", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public ResponseEntity<RestResponse> uploadImage(HttpServletRequest request, @RequestParam(value = "file", required = true) CommonsMultipartFile[] file) throws Exception {
		RestResponse restResponse = null;
		String imagePath = "";
		try {
			String saveDirectory = ConfigurationUtil.config().getString("document.sliderImageDoc");
			String imageName = IdGenerator.generate("sliderimage");
			if (file != null && file.length > 0) {
				for (CommonsMultipartFile aFile : file) {
					if (!aFile.getOriginalFilename().equals("")) {
						String ext = aFile.getFileItem().getName().split("\\.")[1];
						saveDirectory = saveDirectory + imageName + "." + ext;
						imagePath = imageName + "." + ext;
						aFile.transferTo(new File(saveDirectory));
					}
				}
			}
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(imagePath, LocalizationUtil.findLocalizedString("imageuploadsuccess.text")),HttpStatus.OK);			
		} catch (Throwable t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addimage", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> addImage(@RequestBody SliderImageModel sliderImageModel) {
		
		try {
			SliderImageService sliderImageService = ApplicationBeanFactory.getBean(SliderImageService.class);
			sliderImageService.saveImage(sliderImageModel);
				
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("imagesaved.text")), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(sliderImageModel), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/showimage", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> showSlider() {
		RestResponse restResponse = null;
		
		try{
			SliderImageService sliderImageService = ApplicationBeanFactory.getBean(SliderImageService.class);
			List <SliderImage> sliderImageList = sliderImageService.findSliderImages();
			SliderImageModel sliderImageModel = new SliderImageModel();
			List <SliderImageModel> modelList = sliderImageModel.getSliderImageModel(sliderImageList);
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(modelList, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteimage", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> deleteSliderById(@RequestParam(value = "sliderId", required = true) String sliderId) {
		RestResponse restResponse = null;
		
		try{
			SliderImageService sliderImageService = ApplicationBeanFactory.getBean(SliderImageService.class);
			sliderImageService.deleteImage(sliderId);
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("imagedelete.text")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}

}
