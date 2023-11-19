package com.thelocalmarketplace.software;

import java.io.IOException;
import java.math.BigDecimal;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.jjjwelectronics.card.CardReaderBronze;
import com.thelocalmarketplace.hardware.external.CardIssuer;

public class PayWithDebit {
	
	
	
	boolean sysReadyForPayment = false;
	

	BigDecimal amountOwed;
	Card card;
	CardSwipeData cardData;
	CardIssuer cardIssuer;
	String customerSignature;
	CardReaderBronze cardReader;

	
	 public PayWithDebit(CardIssuer issuer,BigDecimal amount) {
	        this.cardIssuer = issuer;
	        this.amountOwed = amount;
	    }
	 
	
	public boolean isSysReadyForPayment() {
		return sysReadyForPayment;
	}

	public void promptForSignature(String signature) {
		customerSignature = signature;
	}
	
	public boolean bankAuthentication(CardSwipeData cardData) {
		long holdNumber = cardIssuer.authorizeHold(cardData.getNumber(), amountOwed.doubleValue());
        boolean transactionResult = cardIssuer.postTransaction(cardData.getNumber(), holdNumber, amountOwed.doubleValue());
        cardIssuer.releaseHold(cardData.getNumber(), holdNumber);
        return transactionResult;
	}
	
public void payViaDebitSwipe(Card card, String signature) throws IOException {
		
		if (sysReadyForPayment==true) {
			System.out.println("Amount Due: "+ amountOwed);
		cardData = (CardSwipeData) cardReader.swipe(card);
		promptForSignature(signature);
		if (bankAuthentication(cardData)==true) {
			System.out.println("Amount Due: 0");
			//print receipt
		}
	    }
	}

public CardIssuer getCardIssuer() {
	return cardIssuer;
}

public BigDecimal getAmountOwed() {
	return amountOwed;
}

public void sysReady() {
	sysReadyForPayment = true;
}


public String getCustomerSignature() {
	return customerSignature;
}

}
