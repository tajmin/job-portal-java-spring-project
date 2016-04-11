package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.data.domain.TransactionHistory;
import com.selvesperer.knoeien.web.controllers.model.TransactionHistoryModel;

public interface TransactionHistoryService {
	
	public List<TransactionHistory> showTransactionInfo(String id);
	
	public List<Object[]> getPaymentReceivedByUser(String id);
	
	public List<Object[]> getPaymentPaidByUser(String id);
	
	public void saveTransactionHistory(TransactionHistoryModel transactionHistoryModel);

}
