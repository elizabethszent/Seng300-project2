package com.thelocalmarketplace.software.test;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.PayWithDebit;

public class PayWithDebitTest {

	
	@Before
    public void setUp() {
    	 /**
         * Sets up the test by creating an instance of the StartSession class.
         */
		CardIssuer cardIssuer = new CardIssuer("SomeName", 4);
		BigDecimal amountOwed = new BigDecimal(20);
        PayWithDebit payWithDebit = new PayWithDebit(cardIssuer,amountOwed);
    }
	
	@Test
	public void testPayWithDebitConstructor() {
        
    }
 
	@Test
	public void testSysReady() {
	
}

	@Test
	public void promptForSignature(String signature) {
	
}
	@Test

	public void testBankAuthentication(CardData cardData) {
	
}

	@Test
	public void testPayViaDebitSwipe(Card card, String signature) throws IOException {
	
	
}
	@Test
	public void testPayViaDebitSwipeListener(Card card, String signature) throws IOException {
	
	
}
	
}
