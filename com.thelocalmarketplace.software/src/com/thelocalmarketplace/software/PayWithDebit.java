package com.thelocalmarketplace.software;

import java.io.IOException;
import java.math.BigDecimal;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.card.AbstractCardReader;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.jjjwelectronics.card.CardReaderBronze;
import com.jjjwelectronics.card.CardReaderListener;
import com.thelocalmarketplace.hardware.external.CardIssuer;

public class PayWithDebit implements CardReaderListener{
	
	
	
	boolean sysReadyForPayment = false;
	

	private BigDecimal amountOwed;
	private Card card;
	private CardSwipeData cardData;
	private CardIssuer cardIssuer;
	private String customerSignature;
	private long holdNumber;
	private int failedAttempts =0; 

	
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
	
	public boolean bankAuthentication(CardSwipeData cardData) {
		holdNumber = cardIssuer.authorizeHold(cardData.getNumber(), amountOwed.doubleValue());
        boolean transactionResult = cardIssuer.postTransaction(cardData.getNumber(), holdNumber, amountOwed.doubleValue());
        cardIssuer.releaseHold(cardData.getNumber(), holdNumber);
        if(transactionResult==false) {
        	failedAttempts++;
        }
        if(failedAttempts>3) {
        	transactionResult=false;
        }
        return transactionResult;
	}
	
public void payViaDebitSwipe(Card card, String signature,AbstractCardReader cardReader) throws IOException {
		
		if (sysReadyForPayment==true && failedAttempts<=3) {
			System.out.println("Amount Due: "+ amountOwed);
		boolean swipeExecuted = false;
		while(swipeExecuted==false) {
			try{cardData = (CardSwipeData) cardReader.swipe(card);
			swipeExecuted=true;
		
		promptForSignature(signature);
		if (bankAuthentication(cardData)==true) {
			amountOwed = new BigDecimal(0);
			System.out.println("Amount Due: " + amountOwed);
			
		}
		else {
			System.out.println("Transaction Failed please try again.");
		}
	    }
		catch(Exception e) {}}	
	}
		else{
			System.out.println("Card blocked.");
		}}


public boolean isSysReadyForPayment() {
	return sysReadyForPayment;
}

public CardIssuer getCardIssuer() {
	return cardIssuer;
}

public BigDecimal getAmountOwed() {
	return amountOwed;
}


public String getCustomerSignature() {
	return customerSignature;
}

@Override
public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) { 
	
}

@Override
public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
	
}

@Override
public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
	 
}

@Override
public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
	 
}

@Override
public void aCardHasBeenSwiped() {
	 
}

@Override
public void theDataFromACardHasBeenRead(CardData data) {
	
}

}
