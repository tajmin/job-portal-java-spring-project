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
	
	//@author SHIFAT edited for settings 
	@Override
	public List<TransactionHistory> showTransactionInfo(String id) {
		// TODO Auto-generated method stub
		List<TransactionHistory> transactionHistory= transactionHistoryRepository.findTransactionHistoryByUserId(id);
		if (transactionHistory == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		
		return transactionHistory;
	}
	
	
	//@author SHIFAT ends
	

	
}
