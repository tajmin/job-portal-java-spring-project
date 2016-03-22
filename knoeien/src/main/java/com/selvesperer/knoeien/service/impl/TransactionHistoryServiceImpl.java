package com.selvesperer.knoeien.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.TransactionHistory;
import com.selvesperer.knoeien.data.repository.TransactionHistoryRepository;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.service.TransactionHistoryService;
import com.selvesperer.knoeien.spring.ScopeType;


@Service("transactionHistoryService")
@Scope(ScopeType.SINGLETON)
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

	
	private static final Logger log = LoggerFactory.getLogger(TransactionHistoryServiceImpl.class);
	@Inject
	private TransactionHistoryRepository transactionHistoryRepository;
	
	@Override
	public List<TransactionHistory> showTransactionInfo(String id) {
		// TODO Auto-generated method stub
		List<TransactionHistory> transactionHistory= transactionHistoryRepository.findTransactionHistoryByUserId(id);
		if (transactionHistory == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		
		return transactionHistory;
	}

	public List<Object[]> getPaymentReceivedByUser(String id) {
		List<Object[]> transactionHistory = transactionHistoryRepository.findTransactionHistoryReceivedByUserId(id);
		if (transactionHistory == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		return transactionHistory;
	}
	
	public List<Object[]> getPaymentPaidByUser(String id) {
		List<Object[]> transactionHistory = transactionHistoryRepository.findTransactionHistoryPaidByUserId(id);
		if (transactionHistory == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		return transactionHistory;
	}

	
}
