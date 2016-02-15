package com.selvesperer.knoeien.data.repository.custom;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.selvesperer.knoeien.data.domain.Job;


public interface JobRepositoryCustom {
	
	@Query("from Job j Order By j.paymnet")
	public List<Job> findJobOrderByPayment();
	
}
