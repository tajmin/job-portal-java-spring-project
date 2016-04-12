package com.selvesperer.knoeien.web.controllers.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.security.SecurityManager;
import com.selvesperer.knoeien.service.JobService;
import com.selvesperer.knoeien.service.TransactionHistoryService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.AppsUtil;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;
import com.selvesperer.knoeien.web.controllers.model.TransactionHistoryModel;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Recipient;
import com.stripe.model.Transfer;
import com.stripe.net.RequestOptions;
import com.stripe.net.RequestOptions.RequestOptionsBuilder;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping(value = "/api/v1/payment")
public class PaymentController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 5218037178787791770L;
	private static final Logger log = (Logger) LoggerFactory.getLogger(PaymentController.class);

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Integer getData() {
		System.out.println(" getting data of transaction ......");
		return 1;
	}

	@RequestMapping(value = "/stripeCharge", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> stripeCharge(@RequestParam(value = "jobID", required = true) String jobID) {
		RestResponse restResponse = null;
		
		if (log.isDebugEnabled()) {
			log.debug("Stripe Charge");
		}
		
		JobService jobService = ApplicationBeanFactory.getBean(JobService.class);
		Object[] jobObj = jobService.makeStripePayment(jobID);
		double d = (Double) jobObj[0];
		String jobCreatedBy =  (String) jobObj[1];
		String jobAssignedTo = "7495-uuid-9444";
		d *= 100;
		int payableAmount = (int) d;		
		String cardNumber = (String) jobObj[3];
		int month = AppsUtil.stringToInt((String) jobObj[4]);
		int year = AppsUtil.stringToInt((String) jobObj[5]);
		
		RequestOptionsBuilder requestOptionsBuilder=new RequestOptionsBuilder();
		final String testSecretKey = ConfigurationUtil.config().getString("stripe.testSecretKey");
		RequestOptions requestOptions = (requestOptionsBuilder).setApiKey(testSecretKey).build();
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		chargeMap.put("amount", payableAmount);
		chargeMap.put("currency", "usd");
		Map<String, Object> cardMap = new HashMap<String, Object>();
		cardMap.put("number", cardNumber);
		cardMap.put("exp_month", month);
		cardMap.put("exp_year", year);
		chargeMap.put("card", cardMap);
		
		TransactionHistoryService transactionHistoryService = ApplicationBeanFactory.getBean(TransactionHistoryService.class);
		TransactionHistoryModel transactionHistoryModel = new TransactionHistoryModel();
		transactionHistoryModel.setFromUserId(jobCreatedBy);
		transactionHistoryModel.setToUserId(jobAssignedTo);
		transactionHistoryModel.setAmount(d);
		transactionHistoryModel.setJobId(jobID);
		try {
			 Charge charge = Charge.create(chargeMap, requestOptions);
	         System.out.println(charge);
	         JSONObject json = (JSONObject) JSONSerializer.toJSON(charge);
	         String chargePaid = json.getString("paid");
	         //if charge successful
	         if(chargePaid.equals("true")) {
	        	 transactionHistoryService.saveTransactionHistory(transactionHistoryModel);
	        }
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("transactioncreatesuccess.text")), HttpStatus.OK);

		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		}
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/stripeTransfer", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> stripeTransfer(@RequestParam(value="stripeToken", required=true) String tokenId) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		RestResponse restResponse = null;
		if (log.isDebugEnabled())
			log.debug("Stripe Transfer");
		
		// Set your secret key: remember to change this to your live secret key in production
		final String liveSecretKey = ConfigurationUtil.config().getString("stripe.liveSecretKey");
		Stripe.apiKey = liveSecretKey;
		//Get recipient
		Map<String, Object> recipientParams = new HashMap<String, Object>();
		recipientParams.put("name", "John Doe");
		recipientParams.put("type", "individual");
		recipientParams.put("card", tokenId); /* for bank account recipientParams.put("bank_account", tokenId);*/
		recipientParams.put("email", "payee@example.com");
		Recipient recipient = Recipient.create(recipientParams);
		// Get the bank account details submitted by the form
		String recipientId=recipient.getId();
		// Create a transfer to the specified recipient 
		Map<String, Object> transferParams = new HashMap<String, Object>();
		transferParams.put("amount", 1000); // amount in cents
		transferParams.put("currency", "usd");
		transferParams.put("recipient", recipientId);
		transferParams.put("card", "cardId");
		transferParams.put("statement_descriptor", "JULY SALES");
		try {
			Transfer transfer = Transfer.create(transferParams);
			System.out.println(transfer);
			return new ResponseEntity<RestResponse>(
					convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("transfersuccess.text")),
					HttpStatus.OK);

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
