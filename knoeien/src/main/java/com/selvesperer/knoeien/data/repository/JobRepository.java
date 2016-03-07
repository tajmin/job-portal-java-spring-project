package com.selvesperer.knoeien.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.data.repository.custom.JobRepositoryCustom;

@Repository
public interface JobRepository extends JpaRepository<Job, String>, JobRepositoryCustom {
	
	@Query("from Job j where j.id = :jobID")
	Job findJobById(@Param("jobID") String jobID);
	
	@Query("from Job j Order by j.totalPrice desc")
	List<Job> findJobOrderByPaymentDesc();
	
	@Query("from Job j Order by j.duration asc")
	List<Job> findJobOrderByDurationAsc();
	
	@Query("from Job j Order by j.deadline asc")
	List<Job> findJobOrderByDateAsc();
	
	@Query("from Job j Where j.draft=false Order by j.createdDate desc")
	List<Job> findJobOrderByCreatedDateDesc();

	@Query("from Job j where j.assignedUserId = :assignedUserId")
	List<Job> findJobByAssignedUserId(@Param("assignedUserId") String assignedUserID);
	
	
	@Query("from Job j where j.createdByID = :createdByID")
	List<Job> findJobByCreatedUserId(@Param("createdByID") String createdByUserId);

	
}