package com.selvesperer.knoeien.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.data.domain.Notification;
import com.selvesperer.knoeien.data.repository.NotificationRepository;
import com.selvesperer.knoeien.data.repository.TransactionHistoryRepository;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.service.NotificationService;
import com.selvesperer.knoeien.spring.ScopeType;
import com.selvesperer.knoeien.web.controllers.model.JobModel;
import com.selvesperer.knoeien.web.controllers.model.NotificationModel;

@Service("notificationService")
@Scope(ScopeType.SINGLETON)
public class NotificationServiceImpl implements NotificationService {

	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
	@Inject
	private NotificationRepository notificationRepository;

	@Override
	public List<NotificationModel> findNotifications(String toUserId, int page, int limit) {
		List<NotificationModel> notificationModelList = notificationRepository.findNotifications(toUserId, page, limit);
		return notificationModelList;
	}

	@Override
	public Notification saveNotification(NotificationModel notificationModel) {

		Notification notification = null;
		if (StringUtils.isNotBlank(notificationModel.getId())) {
			notification = notificationRepository.findNotificationById(notificationModel.getId());
		} else {
			notification = new Notification();
		}
		notification = notification.setNotification(notificationModel);
		return notificationRepository.saveAndFlush(notification);
	}

	@Override
	public void saveSeenNotification(String id) {
		Notification notification = notificationRepository.findNotificationById(id);
		if (notification != null) {
			notification.setHasSeen(true);
		}
		notificationRepository.saveAndFlush(notification);
	}
	
	@Override
	public void deleteNotification(String id) {
		if(id!=null){
		Notification notification = notificationRepository.findNotificationById(id);	
		notificationRepository.delete(notification);
		} 
	}
	
	@Override
	public BigInteger countHasSeeNotification(String id){
		
		return notificationRepository.countHasSeenNotification(id);
	}

	@Override
	public void setAllHasSeenTrue(String id) {
		
		 notificationRepository.setAllHasSeenTrue(id);
		
	}

}
