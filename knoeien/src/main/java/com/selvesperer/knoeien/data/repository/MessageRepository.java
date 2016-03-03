package com.selvesperer.knoeien.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.selvesperer.knoeien.data.domain.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, String>{

	@Query("from Message m where m.toUserId = :toUserId")
	List<Message> findMessageByToUserId(@Param("toUserId") String toUserId);
	
	@Query("from Message m where m.jobId = :jobId")
	List<Message> findMessageByJobId(@Param("jobId") String jobId);
	
}
