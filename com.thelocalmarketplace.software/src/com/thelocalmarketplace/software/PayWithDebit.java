package com.thelocalmarketplace.software;

import java.io.IOException;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.thelocalmarketplace.hardware.external.CardIssuer;

public class PayWithDebit {

	
	
	private boolean sysReadyForPayment = false;
	double amountOwed;
	Card card;
	CardSwipeData cardSwipeData;
	CardData cardData;
	CardIssuer cardIssuer;
	String customerSignature;
	
	public void selectPaymentType() {
		
	}
	public void sysReady() {
		sysReadyForPayment = true;
	}
	
	public void payViaSwipe(Card card) throws IOException {
		if (sysReadyForPayment==true) {
		cardSwipeData = card.swipe();
		cardIssuer.addCardData(cardSwipeData.getCardholder(), card.cardholder, card., customerSignature, amountOwed);}
	}
	

}
