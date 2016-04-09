package com.selvesperer.knoeien.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selvesperer.knoeien.data.domain.Message;
import com.selvesperer.knoeien.data.repository.custom.JobOutRepositoryCustom;

public interface JobOutRepository extends JpaRepository<Message, String>,JobOutRepositoryCustom{

	
}
