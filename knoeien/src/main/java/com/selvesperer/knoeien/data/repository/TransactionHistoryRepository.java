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
	
	@Query("from TransactionHistory t where t.userId = :id")
	List<TransactionHistory> findTransactionHistoryByUserId(@Param("id") String id);

	
}
