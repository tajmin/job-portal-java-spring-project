package com.selvesperer.knoeien.data.repository.custom;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;

import com.mysql.jdbc.Statement;
import com.selvesperer.knoeien.data.domain.Notification;
import com.selvesperer.knoeien.data.enums.FindJobEnum;
import com.selvesperer.knoeien.utils.AppsUtil;
import com.selvesperer.knoeien.utils.DateFormatUtils;
import com.selvesperer.knoeien.utils.QueryUtils;
import com.selvesperer.knoeien.web.controllers.model.JobModel;
import com.selvesperer.knoeien.web.controllers.model.NotificationModel;

import ch.qos.logback.core.net.SyslogOutputStream;

public class NotificationRepositoryImpl implements NotificationRepositoryCustom{

	
	private static final Logger log = LoggerFactory.getLogger(NotificationRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<NotificationModel> findNotifications(String toUserId, int page, int limit) {
		
		StringBuffer queryString = new StringBuffer();
		queryString.append("select n.name,n.description,n.id,n.has_seen from Notification n where n.to_user_id = '" +toUserId+ "'");
		Query query = entityManager.createNativeQuery(queryString.toString());

		if (limit > 0) {
			page = page - 1;
			query.setFirstResult(page * limit);
			query.setMaxResults(limit);
		}

		List<Object[]> results = query.getResultList();
		List<NotificationModel> listOfNotifications = new ArrayList<NotificationModel>();
		for (Object[] result : results) {
			NotificationModel notificationModel = new NotificationModel();
			notificationModel.setName((String) result[0]);
			notificationModel.setDescription((String) result[1]);
			notificationModel.setId((String) result[2]);
			notificationModel.setHasSeen((Boolean)result[3]);
			listOfNotifications.add(notificationModel);
		}
		return listOfNotifications;

	}
	
	@Override
	public BigInteger countHasSeenNotification(String id){
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select count(has_seen) from Notification n where n.has_seen=0 and n.to_user_id='" +id+ "'");
		Query query = entityManager.createNativeQuery(queryString.toString());
		BigInteger bigInteger=(BigInteger) query.getSingleResult();
		System.out.println(bigInteger);
		return bigInteger;
		
	}

	//@Override
	//public void setAllHasSeenTrue(String id) {
		// TODO Auto-generated method stub UPDATE selvesperer.notification n SET n.has_seen = 0 where n.to_user_id=100;
//		StringBuffer queryString = new StringBuffer();
//		queryString.append("UPDATE Notification n SET n.has_seen = 1 where n.to_user_id='" +id+ "'");
//		Query query = entityManager.createNativeQuery(queryString.toString());
//		query.executeUpdate();
//		
//	}
	
	
	
}
