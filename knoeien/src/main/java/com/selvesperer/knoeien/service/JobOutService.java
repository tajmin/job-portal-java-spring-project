package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.web.controllers.model.JobModel;
import com.selvesperer.knoeien.web.controllers.model.MessageModel;

public interface JobOutService {

	public List<MessageModel> showJobOutListsById(String Id,String jobId);
}
