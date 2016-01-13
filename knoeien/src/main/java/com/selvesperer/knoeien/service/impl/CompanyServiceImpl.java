package com.selvesperer.knoeien.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.Company;
import com.selvesperer.knoeien.data.repository.CompanyRepository;
import com.selvesperer.knoeien.service.CompanyService;
import com.selvesperer.knoeien.spring.ScopeType;
import com.selvesperer.knoeien.web.controllers.model.CompanyModel;

@Service("companyService")
@Scope(ScopeType.SINGLETON)
public class CompanyServiceImpl implements CompanyService {
	
	private static final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

	@Inject
	private CompanyRepository companyRepository;
	
	@Override
	public Company findCompanyById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Company saveCompany(CompanyModel companyModel) {		
		Company company = new Company();
		company.setName(companyModel.getName());
		company.setDescription(companyModel.getDescription());
		company.setContactNumber(companyModel.getContactNumber());
		company.setEmail(companyModel.getEmail());
		company.setUrl(companyModel.getUrl());
		company.setAddressLine1(companyModel.getAddressLine1());
		company.setAddressLine2(companyModel.getAddressLine2());
		company.setAddressLine3(companyModel.getAddressLine3());
		company.setZip(companyModel.getZip());
		company.setCity(companyModel.getCity());
		company.setState(companyModel.getState());
		company.setCountry(companyModel.getCountry());
		company.setLatitude(companyModel.getLatitude());
		company.setLongitude(companyModel.getLongitude());
		company.setFacebook(companyModel.getFacebook());
		company.setGooglePlus(companyModel.getGooglePlus());
		company.setLinkedin(companyModel.getLinkedin());
		company.setRecentJobPeriod(companyModel.getRecentJobPeriod());
		company.setBestJobAmount(companyModel.getBestJobAmount());
		company.setShortestTimeJobPeriod(companyModel.getShortestTimeJobPeriod());
		company.setEarliestDateJobPeriod(companyModel.getEarliestDateJobPeriod());
		
		return companyRepository.saveAndFlush(company);
	}

	
}
