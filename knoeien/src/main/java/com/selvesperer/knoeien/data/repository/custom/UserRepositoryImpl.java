package com.selvesperer.knoeien.data.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRepositoryImpl implements UserRepositoryCustom {

	private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	
}
