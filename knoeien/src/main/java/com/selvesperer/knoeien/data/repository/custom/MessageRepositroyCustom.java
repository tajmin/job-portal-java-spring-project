package com.selvesperer.knoeien.data.repository.custom;

import java.util.List;

import com.selvesperer.knoeien.web.controllers.model.MessageModel;

public interface MessageRepositroyCustom {
	public List<MessageModel> findAllMessages(String jobId, String userId, int page, int limit);
	
	public List<MessageModel> findAllMessagesBySeekerId(String jobId,String jobSeekerId,String userId, int page, int limit);
}
