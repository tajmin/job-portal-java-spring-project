package com.selvesperer.knoeien.data.repository.custom;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.utils.AppsUtil;
import com.selvesperer.knoeien.utils.DateFormatUtils;
import com.selvesperer.knoeien.utils.QueryUtils;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

public class JobInterestedRepositoryImpl implements JobInterestdRepositoryCustom {

	private static final Logger log = LoggerFactory.getLogger(JobInterestedRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public double findLowestBid(String jobId) {
		double lowestBid = 0.0;
		try{
			StringBuilder queryString = new StringBuilder();
			queryString.append("select min(ji.bid_amount)");
			queryString.append(" from job_interested ji");
			queryString.append(" where ji.job_id='").append(jobId).append("'");
	
			Query query = entityManager.createNativeQuery(queryString.toString());
	
			@SuppressWarnings("unchecked")
			List<Object> results = query.getResultList();
			if(results.size() > 0){
				lowestBid = Double.parseDouble(results.get(0).toString());
			}
		}catch(Throwable t){
			log.error(t.getLocalizedMessage());
		}
		return lowestBid;
	}
	
	@Override
	public List<JobModel> findAllJobByInterestedUserId(String userId, int page, int limit) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("select j.id,j.title, j.address_line_1, j.price, j.hours, j.deadline, j.minutes, j.created_date, j.image_url,");
		queryString.append(" j.latitude, j.longitude");
		queryString.append(" from job_interested ji join job j on ji.job_id=j.id");
		queryString.append(" where ji.job_interested_user_id='").append(userId).append("'");

		Query query = entityManager.createNativeQuery(queryString.toString());

		if (limit > 0) {
			page = page - 1;
			query.setFirstResult(page * limit);
			query.setMaxResults(limit);
		}

		@SuppressWarnings("unchecked")
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
}
