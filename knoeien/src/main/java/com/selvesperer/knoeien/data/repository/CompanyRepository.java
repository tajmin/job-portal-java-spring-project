package com.selvesperer.knoeien.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.selvesperer.knoeien.data.domain.Company;
import com.selvesperer.knoeien.data.repository.custom.CompanyRepositoryCustom;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String>, CompanyRepositoryCustom {
	@Query("from Company c where c.companyId = :companyID")
	List<Company> findCompanyByCompanyId(@Param("companyID") String companyID);	
}