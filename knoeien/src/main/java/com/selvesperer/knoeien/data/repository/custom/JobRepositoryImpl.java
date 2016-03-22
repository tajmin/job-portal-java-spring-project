package com.selvesperer.knoeien.data.repository.custom;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.data.enums.FindJobEnum;
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
			//jobModel.setPrice((String) result[3].toString());
			jobModel.setHours(QueryUtils.parseInteger(result[4], false));
			//jobModel.setDeadline((String) result[5].toString());
			//jobModel.setMinutes(QueryUtils.parseInteger(result[6], false));
			//jobModel.setCreatedDate((String) result[7].toString());
			jobModel.setImageUrl((String) result[8]);
			listOfJobs.add(jobModel);
		}
		return listOfJobs;

	}

}
