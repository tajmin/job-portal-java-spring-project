package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.data.domain.TransactionHistory;

public interface TransactionHistoryService {
	
	public List<TransactionHistory> showTransactionInfo(String id);
	
	public List<TransactionHistory> getPaymentReceivedByUser(String id);
	
	public List<TransactionHistory> getPaymentPaidByUser(String id);

}
