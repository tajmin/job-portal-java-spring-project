package com.selvesperer.knoeien.data.repository.custom;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.data.enums.FindJobEnum;
import com.selvesperer.knoeien.utils.AppsUtil;
import com.selvesperer.knoeien.utils.DateFormatUtils;
import com.selvesperer.knoeien.utils.QueryUtils;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

public class JobRepositoryImpl implements JobRepositoryCustom {

	private static final Logger log = LoggerFactory.getLogger(JobRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<JobModel> findJobs(String type, int page, int limit) {
		FindJobEnum findJobEnum = FindJobEnum.getFindJobEnum(type);
		StringBuffer queryString = new StringBuffer();
		queryString.append("select j.id,j.title, j.address_line_1, j.price, j.hours, j.deadline, j.minutes, j.created_date, j.image_url,");
		queryString.append(" j.latitude, j.longitude");
		queryString.append(" from job j");
		queryString.append(" order by j.").append(findJobEnum.getOrderBy()).append(" ").append(findJobEnum.getOrderType());

		Query query = entityManager.createNativeQuery(queryString.toString());

		if (limit > 0) {
			page = page - 1;
			query.setFirstResult(page * limit);
			query.setMaxResults(limit);
		}

		List<Object[]> results = query.getResultList();
		List<JobModel> listOfJobs = new ArrayList<JobModel>();
		for (Object[] result : results) {
			JobModel jobModel = new JobModel();
			jobModel.setId((String) result[0]);
			jobModel.setTitle((String) result[1]);
			jobModel.setAddressLine1((String) result[2]);
			jobModel.setPrice(AppsUtil.doubleToString((Double) result[3]));
			jobModel.setHours(QueryUtils.parseInteger(result[4], false));
			jobModel.setDeadline(DateFormatUtils.getWebDateFromTimestamp((Timestamp) result[5]));
			jobModel.setMinutes(QueryUtils.parseInteger(result[6], false));
			jobModel.setCreatedDate(DateFormatUtils.getDBFormattedFromTimestamp((Timestamp) result[7]));			
			jobModel.setWhenPosted(AppsUtil.getDiffenrence(jobModel.getCreatedDate()));

			jobModel.setImageUrl((String) result[8]);
			jobModel.setLatitude((String) result[9]);
			jobModel.setLongitude((String) result[10]);
			listOfJobs.add(jobModel);
		}
		return listOfJobs;

	}

	@Override
	public List<JobModel> findJobByCreatedUserId(String id, int page, int limit) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select j.id, j.title, j.price, j.image_url, j.draft from job j");
		queryString.append(" where j.created_by_id= '" + id +"'");
		queryString.append(" order by j.created_date desc");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		
		if (limit > 0) {
			page = page - 1;
			query.setFirstResult(page * limit);
			query.setMaxResults(limit);
		}
		
		List<Object[]> results = query.getResultList();
		List<JobModel> listOfJobs = new ArrayList<JobModel>();
		for (Object[] result : results) {
			JobModel jobModel = new JobModel();
			jobModel.setId((String) result[0]);
			jobModel.setTitle((String) result[1]);
			jobModel.setPrice(AppsUtil.doubleToString((Double) result[2]));
			jobModel.setImageUrl((String) result[3]);
			jobModel.setDraft((boolean) result[4]);
			
			listOfJobs.add(jobModel);
		}
		return listOfJobs;
	}

	@Override
	public List<JobModel> findJobByAssignedUserId(String id, int page, int limit) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select j.id, j.title, j.price, j.image_url from job j");
		queryString.append(" where j.assigned_user_id= '" + id +"'");
		queryString.append(" order by j.created_date desc");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		
		if (limit > 0) {
			page = page - 1;
			query.setFirstResult(page * limit);
			query.setMaxResults(limit);
		}
		
		List<Object[]> results = query.getResultList();
		List<JobModel> listOfJobs = new ArrayList<JobModel>();
		for (Object[] result : results) {
			JobModel jobModel = new JobModel();
			jobModel.setId((String) result[0]);
			jobModel.setTitle((String) result[1]);
			jobModel.setPrice(AppsUtil.doubleToString((Double) result[2]));
			jobModel.setImageUrl((String) result[3]);
			
			listOfJobs.add(jobModel);
		}
		return listOfJobs;
	}

}
