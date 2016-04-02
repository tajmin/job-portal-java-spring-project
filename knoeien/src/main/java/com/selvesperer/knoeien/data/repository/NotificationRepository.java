package com.selvesperer.knoeien.data.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.data.domain.Notification;
import com.selvesperer.knoeien.data.domain.TransactionHistory;
import com.selvesperer.knoeien.data.repository.custom.NotificationRepositoryCustom;
import com.selvesperer.knoeien.data.repository.custom.TransactionHistoryRepositoryCustom;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, String>, NotificationRepositoryCustom {

	@Query("from Notification n where n.toUserId = :toUserId")
	List<Notification> findNotificationListById(@Param("toUserId") String toUserId);
	
	@Query("from Notification n where n.id = :id")
	Notification findNotificationById(@Param("id") String id);
	
	@Query(value="Select count(has_seen) from Notification n where n.has_seen=0 and n.id=101",nativeQuery=true)
	BigInteger countHasSeenNotification(@Param("id") String id);
	
	//Select count(has_seen) from selvesperer.notification n where n.has_seen=1
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notification n SET n.hasSeen = 1 where n.toUserId=:toUserId")
    public void setAllHasSeenTrue(@Param("toUserId") String id);

}
