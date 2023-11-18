package com.thelocalmarketplace.software;

import java.io.IOException;
import java.math.BigDecimal;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.CardReaderBronze;
import com.thelocalmarketplace.hardware.external.CardIssuer;

public class PayWithDebit {
	
	
	private boolean sysReadyForPayment = false;
	BigDecimal amountOwed;
	Card card;
	CardData cardData;
	CardIssuer cardIssuer;
	String customerSignature;
	CardReaderBronze cardReader;
	
	 public PayWithDebit(CardIssuer issuer,BigDecimal amount) {
	        this.cardIssuer = issuer;
	        this.amountOwed = amount;
	    }
	 
	public void sysReady() {
		sysReadyForPayment = true;
	}
	
	public void promptForSignature(String signature) {
		customerSignature = signature;
	}
	
	public boolean bankAuthentication(CardData cardData) {
		long holdNumber = cardIssuer.authorizeHold(cardData.getNumber(), amountOwed.doubleValue());
        boolean transactionResult = cardIssuer.postTransaction(cardData.getNumber(), holdNumber, amountOwed.doubleValue());
        cardIssuer.releaseHold(cardData.getNumber(), holdNumber);
        return transactionResult;
	}
	
public void payViaDebitSwipe(Card card, String signature) throws IOException {
		
		if (sysReadyForPayment==true) {
			System.out.println("Amount Due: "+ amountOwed);
		cardData = cardReader.swipe(card);
		promptForSignature(signature);
		if (bankAuthentication(cardData)==true) {
			System.out.println("Amount Due: 0");
			//print receipt
		}
	    }
	}
	

}
