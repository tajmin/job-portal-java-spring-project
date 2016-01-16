package com.selvesperer.knoeien.web.controllers.rest;

import java.io.Serializable;

import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selvesperer.knoeien.data.domain.Company;
import com.selvesperer.knoeien.service.CompanyService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;
import com.selvesperer.knoeien.web.controllers.model.CompanyModel;

@Controller
@RequestMapping(value = "/api/v1/company")
public class CompanyController extends AbstractController implements Serializable {

	private static final long serialVersionUID = -7064518971040688482L;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(CompanyController.class);

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Integer getData() {
		System.out.println("hay i amd here in get data for you.................................");
		return 1;
	}
	
	@RequestMapping(method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> addCompany(@RequestBody CompanyModel companyModel) {		
		Company company = null;
		try {
			CompanyService companyService = ApplicationBeanFactory.getBean(CompanyService.class);
			company = companyService.saveCompany(companyModel);
		} catch (Exception ex) {
			Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(company), HttpStatus.OK);
	}
}
