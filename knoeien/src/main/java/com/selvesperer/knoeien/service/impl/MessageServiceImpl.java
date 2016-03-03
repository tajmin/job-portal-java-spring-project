package com.selvesperer.knoeien.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.data.domain.Message;
import com.selvesperer.knoeien.data.repository.MessageRepository;

import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.service.MessageService;
import com.selvesperer.knoeien.spring.ScopeType;
import com.selvesperer.knoeien.web.controllers.model.MessageModel;

@Service("messageService")
@Scope(ScopeType.SINGLETON)
public class MessageServiceImpl implements MessageService{

	
	private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	
	@Inject
	private MessageRepository messageRepository;
	
	
	@Override
	public List<Message> showMessageByToUserId(String toUserId) {
		// TODO Auto-generated method stub
		
		List<Message> message=messageRepository.findMessageByToUserId(toUserId);
		if(message==null)
			throw new AuthenticationFailedException("error.usernotfound.text");
			
			
		return message;
	}

	@Override
	public List<Message> showMessageByJobId(String jobId) {
		// TODO Auto-generated method stub
		
		List<Message> message=messageRepository.findMessageByJobId(jobId);
		
		if(message==null)
			throw new AuthenticationFailedException("error.usernotfound.text");
		return message;
	}

	@Override
	public Message saveMessage(MessageModel messageModel) {
		// TODO Auto-generated method stub
		Message message = new Message(messageModel);
		return messageRepository.saveAndFlush(message);
	}

}
