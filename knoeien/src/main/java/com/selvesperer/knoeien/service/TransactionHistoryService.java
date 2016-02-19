package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.data.domain.TransactionHistory;

public interface TransactionHistoryService {
	public List<TransactionHistory> showTransactionInfo(String id);
	
	

}
