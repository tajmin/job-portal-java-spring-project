package com.selvesperer.knoeien.service;

import com.selvesperer.knoeien.data.domain.Company;
import com.selvesperer.knoeien.web.controllers.model.CompanyModel;

public interface CompanyService {
	
	public Company findCompanyById(String id);

	public Company saveCompany(CompanyModel companyModel);
	
	public void updateComapny(CompanyModel companyModel, String id);
	
}
