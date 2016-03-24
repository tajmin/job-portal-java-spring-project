package com.selvesperer.knoeien.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.selvesperer.knoeien.data.domain.JobInterested;

public interface JobInterestedRepository extends JpaRepository<JobInterested, String>{

	@Query("from JobInterested ji where ji.id = :id")
	JobInterested findById(@Param("id") String id);
	
	@Query("from JobInterested ji where ji.id = :jobId")
	JobInterested findJobById(@Param("jobId") String jobId);
	
	@Query("from JobInterested ji where ji.jobInterestedUserId = :userId")
	List<JobInterested> findJobByInterestedUserId(@Param("userId") String userId);
	
//	@Query(value="select * from JobInterested",nativeQuery=true)
//	List<JobInterested> findLowestBidAmount();
}
