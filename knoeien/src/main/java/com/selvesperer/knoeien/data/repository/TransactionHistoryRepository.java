package com.selvesperer.knoeien.data.repository;

import java.util.List;

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
	
	@Query("from TransactionHistory t where t.createdByID = :id")
	List<TransactionHistory> findTransactionHistoryByUserId(@Param("id") String id);
	
	@Query(value="SELECT t.amount, t.created_by_name, t.created_date, j.title FROM transaction_history t join job j on t.job_id=j.id where t.to_user_id = :toUserId Order by t.created_date desc",nativeQuery=true)
	List<Object[]> findTransactionHistoryReceivedByUserId(@Param("toUserId") String toUserId);
	
	@Query(value="SELECT t.amount, t.created_by_name, t.created_date, j.title FROM transaction_history t join job j on t.job_id=j.id where t.from_user_id = :fromUserId Order by t.created_date desc",nativeQuery=true)
	List<Object[]> findTransactionHistoryPaidByUserId(@Param("fromUserId") String fromUserId);

	
}
