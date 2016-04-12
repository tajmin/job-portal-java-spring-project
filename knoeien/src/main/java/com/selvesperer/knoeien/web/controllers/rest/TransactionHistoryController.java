package com.selvesperer.knoeien.web.controllers.rest;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selvesperer.knoeien.data.domain.TransactionHistory;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.security.SecurityManager;
import com.selvesperer.knoeien.service.TransactionHistoryService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;
import com.selvesperer.knoeien.web.controllers.model.TransactionHistoryModel;

@Controller
@RequestMapping(value = "/api/v1/transaction")
public class TransactionHistoryController extends AbstractController implements Serializable{

	private static final long serialVersionUID = 4693779095855653551L;
	private static final Logger log = (Logger) LoggerFactory.getLogger(TransactionHistoryController.class);

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Integer getData() {
		System.out.println(" getting data of transaction ......");
		return 1;
	}
	
	@RequestMapping(value = "/transactionInfo", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> balanceInfo() {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Transaction Info");		
		try {
			TransactionHistoryService transactionHistoryService = ApplicationBeanFactory.getBean(TransactionHistoryService.class);
			String id = SecurityManager.getCurrentUserId();
			List<TransactionHistory> transactionHistory = transactionHistoryService.showTransactionInfo(id);
			TransactionHistoryModel transactionHistoryModel = new TransactionHistoryModel(transactionHistory.get(0));
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(transactionHistoryModel, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
			
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		}
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
	}
	@RequestMapping(value = "/paymentReceived", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> paymentReceived() {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Transaction Info");		
		try {
			TransactionHistoryService transactionHistoryService = ApplicationBeanFactory.getBean(TransactionHistoryService.class);
			String id = SecurityManager.getCurrentUserId();
			List<Object[]> transactionHistoryList = transactionHistoryService.getPaymentReceivedByUser(id);
			TransactionHistoryModel transactionHistoryModel = new TransactionHistoryModel();			
			List<TransactionHistoryModel> transactionHistoryModelList = transactionHistoryModel.getModelList(transactionHistoryList);
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(transactionHistoryModelList, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
			
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		}
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/paymentMade", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> paymentMade() {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Transaction Info");		
		try {
			TransactionHistoryService transactionHistoryService = ApplicationBeanFactory.getBean(TransactionHistoryService.class);
			String id = SecurityManager.getCurrentUserId();
			List<Object[]> transactionHistoryList = transactionHistoryService.getPaymentPaidByUser(id);
			TransactionHistoryModel transactionHistoryModel = new TransactionHistoryModel();			
			List<TransactionHistoryModel> transactionHistoryModelList = transactionHistoryModel.getModelList(transactionHistoryList);
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(transactionHistoryModelList, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
			
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		}
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
	}
}
