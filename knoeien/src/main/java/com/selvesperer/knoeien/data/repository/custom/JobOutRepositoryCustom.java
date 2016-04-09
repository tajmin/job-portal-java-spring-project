package com.selvesperer.knoeien.data.repository.custom;

import java.util.List;

import com.selvesperer.knoeien.web.controllers.model.JobModel;
import com.selvesperer.knoeien.web.controllers.model.MessageModel;

public interface JobOutRepositoryCustom {

	public List<MessageModel> showJobOutListsById(String Id,String jobId);
}
