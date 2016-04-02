package com.selvesperer.knoeien.data.repository.custom;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.selvesperer.knoeien.data.domain.Notification;
import com.selvesperer.knoeien.web.controllers.model.JobModel;
import com.selvesperer.knoeien.web.controllers.model.NotificationModel;

public interface NotificationRepositoryCustom {

	public List<NotificationModel> findNotifications(String type, int page, int limit);
	
	public BigInteger countHasSeenNotification(String id);
	
	//public void setAllHasSeenTrue(String id);
}
