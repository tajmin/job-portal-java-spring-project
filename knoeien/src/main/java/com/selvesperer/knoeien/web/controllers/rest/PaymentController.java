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
import org.springframework.web.bind.annotation.ResponseBody;

import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import com.stripe.net.RequestOptions.RequestOptionsBuilder;

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

	@RequestMapping(value = "/stripeCharge", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> stripeCharge() {
		RestResponse restResponse = null;
		if (log.isDebugEnabled())
			log.debug("Stripe Charge");
		RequestOptionsBuilder requestOptionsBuilder=new RequestOptionsBuilder();
		final String testSecretKey = ConfigurationUtil.config().getString("stripe.testSecretKey");
		RequestOptions requestOptions = (requestOptionsBuilder).setApiKey(testSecretKey).build();
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		chargeMap.put("amount", 100);
		chargeMap.put("currency", "usd");
		Map<String, Object> cardMap = new HashMap<String, Object>();
		cardMap.put("number", "4242424242424242");
		cardMap.put("exp_month", 12);
		cardMap.put("exp_year", 2020);
		chargeMap.put("card", cardMap);
		try {
			 Charge charge = Charge.create(chargeMap, requestOptions);
	         System.out.println(charge);
			return new ResponseEntity<RestResponse>(
					convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("signupsuccess.text")),
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
