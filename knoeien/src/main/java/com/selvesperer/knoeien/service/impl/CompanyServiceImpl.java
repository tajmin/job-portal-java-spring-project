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
		Company company = companyRepository.findCompanyById();
		return company;
	}
	
	@Override
	public Company saveCompany(CompanyModel companyModel) {		
		return companyRepository.saveAndFlush(new Company(companyModel));
	}

	@Override
	public void updateComapny(CompanyModel companyModel, String id) {
		Company company = companyRepository.findCompanyById();
		
		if(company == null) {
			//companyModel.setName("Helper Limited");
			companyRepository.saveAndFlush(new Company(companyModel));
		} else {
			company.setVat(companyModel.getVat());
			company.setPromocode(companyModel.getPromocode());
			company.setSalescode(companyModel.getSalescode());
			company.setName(companyModel.getName());
			companyRepository.saveAndFlush(company);
		}
		
	}
	
}
