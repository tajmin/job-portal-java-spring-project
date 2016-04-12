package com.selvesperer.knoeien.data.repository.custom;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.selvesperer.knoeien.web.controllers.model.MessageModel;

public class MessageRepositoryImpl implements MessageRepositroyCustom{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<MessageModel> findAllMessages(String jobId, String userId, int page, int limit) {
		StringBuilder jql = new StringBuilder();
		jql.append("select m.id, m.user_message, m.job_id, m.from_user_id, m.to_user_id,");
		jql.append(" fu.background_image_url as fu_image, tu.background_image_url as tu_image, (m.from_user_id='").append(userId).append("') as me");
		jql.append(" from message m join user tu on (tu.id=m.to_user_id) join user fu on (fu.id=m.from_user_id)");
		jql.append(" where m.job_id = '").append(jobId).append("'");
		jql.append(" and (m.to_user_id = '").append(userId).append("'");
		jql.append(" or m.from_user_id = '").append(userId).append("')");
		jql.append(" order by m.created_date desc");

		Query query = entityManager.createNativeQuery(jql.toString());

		if (limit > 0) {
			page = page - 1;
			query.setFirstResult(page * limit);
			query.setMaxResults(limit);
		}

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		List<MessageModel> listOfMessage = new ArrayList<MessageModel>();
		//for (Object[] result : results) {
		for(int i=results.size()-1; i>=0; i--){
			Object[] result = (Object[])results.get(i);
			MessageModel messageModel = new MessageModel();
			messageModel.setId((String) result[0]);
			messageModel.setUserMessage((String) result[1]);
			messageModel.setJobId((String) result[2]);
			messageModel.setFromUserId((String) result[3]);
			messageModel.setToUserId((String) result[4]);
			messageModel.setFromUserBackgroundImageUrl((String) result[5]);
			messageModel.setToUserBackgroundImageUrl((String) result[6]);
			messageModel.setMe(String.valueOf((Integer) result[7]));
			listOfMessage.add(messageModel);
		}
		return listOfMessage;
	}
	
	@Override
	public List<MessageModel> findAllMessagesBySeekerId(String jobId,String jobSeekerId,String jobPosterId, int page, int limit) {
		StringBuilder jql = new StringBuilder();
		jql.append("select m.id, m.user_message, m.job_id, m.from_user_id, m.to_user_id,");
		jql.append(" fu.background_image_url as fu_image, tu.background_image_url as tu_image, (m.from_user_id='").append(jobPosterId).append("') as me");
		jql.append(" from message m join user tu on (tu.id=m.to_user_id) join user fu on (fu.id=m.from_user_id)");
		jql.append(" where m.job_id = '").append(jobId).append("'");
		
		jql.append(" and (m.to_user_id = '").append(jobSeekerId).append("'").append(" and m.from_user_id = '").append(jobPosterId).append("')");		
		jql.append(" or (m.to_user_id = '").append(jobPosterId).append("'").append(" and m.from_user_id = '").append(jobSeekerId).append("')");
		
		jql.append(" order by m.created_date desc");

		Query query = entityManager.createNativeQuery(jql.toString());

		if (limit > 0) {
			page = page - 1;
			query.setFirstResult(page * limit);
			query.setMaxResults(limit);
		}

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		List<MessageModel> listOfMessage = new ArrayList<MessageModel>();
		//for (Object[] result : results) {
		for(int i=results.size()-1; i>=0; i--){
			Object[] result = (Object[])results.get(i);
			MessageModel messageModel = new MessageModel();
			messageModel.setId((String) result[0]);
			messageModel.setUserMessage((String) result[1]);
			messageModel.setJobId((String) result[2]);
			messageModel.setFromUserId((String) result[3]);
			messageModel.setToUserId((String) result[4]);
			messageModel.setFromUserBackgroundImageUrl((String) result[5]);
			messageModel.setToUserBackgroundImageUrl((String) result[6]);
			messageModel.setMe(String.valueOf((Integer) result[7]));
			listOfMessage.add(messageModel);
		}
		return listOfMessage;
	}
	
}
