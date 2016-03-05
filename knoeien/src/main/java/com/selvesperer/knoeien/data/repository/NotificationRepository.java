package com.selvesperer.knoeien.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.selvesperer.knoeien.data.domain.Notification;
import com.selvesperer.knoeien.data.domain.TransactionHistory;
import com.selvesperer.knoeien.data.repository.custom.NotificationRepositoryCustom;
import com.selvesperer.knoeien.data.repository.custom.TransactionHistoryRepositoryCustom;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, String>, NotificationRepositoryCustom {

	@Query("from Notification n where n.id = :id")
	List<Notification> findNotificationById(@Param("id") String id);
}
