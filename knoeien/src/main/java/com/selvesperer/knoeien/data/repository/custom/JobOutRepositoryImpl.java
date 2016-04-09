package com.selvesperer.knoeien.data.repository.custom;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.utils.AppsUtil;
import com.selvesperer.knoeien.web.controllers.model.MessageModel;

public class JobOutRepositoryImpl implements JobOutRepositoryCustom{

	private static final Logger log = LoggerFactory.getLogger(JobOutRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<MessageModel> showJobOutListsById(String Id,String jobId) {
		StringBuffer queryString = new StringBuffer();
		//String jobId="46002544-3650-uuid-8f9c-8b0641fd1fea";
		//String Id1="45975303-2462-uuid-8718-db3d5754b154";
		queryString.append("select m.user_message,m.job_id,j.price,u.last_name,count(distinct(m.id)),u.background_image_url,u.first_name, ji.bid_amount,j.title,j.description ");
		queryString.append("from message m");
		queryString.append(" join user u on (m.from_user_id=u.id)");
		queryString.append(" join job_interested ji on ( m.from_user_id =ji.job_interested_user_id and m.job_id = ji.job_id)");
		queryString.append(" join job j on (j.id=m.job_id)");
		queryString.append(" where m.to_user_id='").append(Id).append("'").append(" and m.job_id='").append(jobId).append("'"); 
		queryString.append(" group by m.from_user_id");
		
		Query query = entityManager.createNativeQuery(queryString.toString());
		
		List<Object[]> results = query.getResultList();
		List<MessageModel> listOfMessages = new ArrayList<MessageModel>();
		for (Object[] result : results) {
			MessageModel messageModel = new MessageModel();			
			messageModel.setUserMessage((String)result[0]);
			messageModel.setJobId((String)result[1]);
			messageModel.setPrice(AppsUtil.doubleToString((Double) result[2]));
			messageModel.setLastname((String)result[3]);
			messageModel.setCountMessageId((BigInteger)result[4]);
			messageModel.setBackgroundImageUrl((String)result[5]);
			messageModel.setFirstname((String)result[6]);
			messageModel.setBidAmount(AppsUtil.doubleToString((Double)result[7]));
			messageModel.setJobTitle((String)result[8]);
			messageModel.setJobDescription((String)result[9]);

			listOfMessages.add(messageModel);
		}
		
		return listOfMessages;
	}
}
