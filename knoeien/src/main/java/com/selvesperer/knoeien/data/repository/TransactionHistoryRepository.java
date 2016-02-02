package com.selvesperer.knoeien.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.selvesperer.knoeien.data.domain.TransactionHistory;
import com.selvesperer.knoeien.data.repository.custom.TransactionHistoryRepositoryCustom;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, String>, TransactionHistoryRepositoryCustom {
	
	@Query("from TransactionHistory t where t.id = :id")
	TransactionHistory findTransactionHistoryById(@Param("id") String id);
	
//	@Query("select t from TransactionHistory t")	
//	List<TransactionHistory> findAllTransactionHistory(@Param("id")String id,@Param("amount")String amount, @Param("jobId")String jobId,@Param("toUserId")String to_user_id);
	
}
