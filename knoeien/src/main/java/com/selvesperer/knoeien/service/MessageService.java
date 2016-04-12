package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.data.domain.Message;
import com.selvesperer.knoeien.web.controllers.model.MessageModel;

public interface MessageService {

	List<Message> showMessageByToUserId(String toUserId);
	List<Message> showMessageByJobId(String jobId);
	List<MessageModel> showMessageByJobId(String jobId, String userId, int page, int limit);
	public Message saveMessage(MessageModel messageModel);
	
	List<MessageModel> findAllMessagesBySeekerId(String jobId,String jobSeekerId,String userId, int page, int limit);
	
}
