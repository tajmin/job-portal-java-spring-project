package com.selvesperer.knoeien.data.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;

import com.selvesperer.knoeien.data.domain.Job;

public class JobRepositoryImpl implements JobRepositoryCustom {

	private static final Logger log = LoggerFactory.getLogger(JobRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Job> findJobOrderByPayment() {
		return null;
	}
	
}
