package com.selvesperer.knoeien.data.repository;

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
	
	
	
}