package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.data.domain.TransactionHistory;
import com.selvesperer.knoeien.web.controllers.model.TransactionHistoryModel;

public interface TransactionHistoryService {
	public List<TransactionHistory> showTransactionInfo(String id);
	
	public void saveTransactionHistory(TransactionHistoryModel transactionHistoryModel,String id);

}
