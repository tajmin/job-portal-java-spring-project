package com.selvesperer.knoeien.data.repository.custom;

import java.util.List;

import com.selvesperer.knoeien.web.controllers.model.JobModel;

public interface JobRepositoryCustom {
	public List<JobModel> findJobs(String type, int page, int limit);
}
