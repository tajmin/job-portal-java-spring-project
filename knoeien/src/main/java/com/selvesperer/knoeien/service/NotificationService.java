package com.selvesperer.knoeien.service;

import java.math.BigInteger;
import java.util.List;

import com.selvesperer.knoeien.data.domain.Notification;
import com.selvesperer.knoeien.web.controllers.model.JobModel;
import com.selvesperer.knoeien.web.controllers.model.NotificationModel;
import com.selvesperer.knoeien.web.controllers.model.UserModel;

public interface NotificationService {

	public List<NotificationModel> findNotifications(String toUserId, int page, int limit);
	
	public Notification saveNotification(NotificationModel notificationModel);
	
	public void saveSeenNotification(String id);
	
	public void deleteNotification(String id);
	
	public BigInteger countHasSeeNotification(String id);
	
	public void setAllHasSeenTrue(String id);
}
