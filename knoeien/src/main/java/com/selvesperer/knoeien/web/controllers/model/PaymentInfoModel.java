package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;

import com.selvesperer.knoeien.data.domain.User;

public class PaymentInfoModel implements Serializable {

	private static final long serialVersionUID = 2361372848872111233L;

	private String cardNumber;
	
	private String cardholderName;
	
	private String accountNumber;
	
	private String cardMonth;
	
	private String cardYear;
	
	private String cvc;
	
	public PaymentInfoModel() {}
	
	public PaymentInfoModel(User user) {
		super();
		this.setCardNumber(user.getCardNumber());
		this.setCardholderName(user.getCardholderName());
		this.setAccountNumber(user.getAccountNumber());
		this.setCardMonth(user.getCardMonth());
		this.setCardYear(user.getCardYear());
		this.setCvc(user.getCvc());
	}
	
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCardMonth() {
		return cardMonth;
	}

	public void setCardMonth(String cardMonth) {
		this.cardMonth = cardMonth;
	}

	public String getCardYear() {
		return cardYear;
	}

	public void setCardYear(String cardYear) {
		this.cardYear = cardYear;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

		
}
