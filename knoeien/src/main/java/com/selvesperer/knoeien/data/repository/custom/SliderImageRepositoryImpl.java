package com.selvesperer.knoeien.data.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class SliderImageRepositoryImpl implements SliderImageRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;

}
