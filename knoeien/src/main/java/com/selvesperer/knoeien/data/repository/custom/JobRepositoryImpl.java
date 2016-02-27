package com.selvesperer.knoeien.data.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JobRepositoryImpl implements JobRepositoryCustom {

	private static final Logger log = LoggerFactory.getLogger(JobRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	//@Query("from Job j Order by j.payment desc")
	//List<Job> findJobOrderByPaymentDesc();
	
	
}
