package com.selvesperer.knoeien.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.Notification;
import com.selvesperer.knoeien.data.repository.NotificationRepository;
import com.selvesperer.knoeien.data.repository.TransactionHistoryRepository;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.service.NotificationService;
import com.selvesperer.knoeien.spring.ScopeType;

@Service("notificationService")
@Scope(ScopeType.SINGLETON)
public class NotificationServiceImpl implements NotificationService {

	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
	@Inject
	private NotificationRepository notificationRepository;
	@Override
	public List<Notification> showNotification(String id) {
		// TODO Auto-generated method stub
		
		List<Notification> notification=notificationRepository.findNotificationById(id);
		
		if(notification==null){
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		
		return notification;
	}
	
	
	
	
	
}
