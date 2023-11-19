package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.PayWithDebit;

public class PayWithDebitTest {
	CardIssuer cardIssuer;
	BigDecimal amountOwed;
    PayWithDebit payWithDebit;
    Card card;
    Card.CardSwipeData cardData;
	
  
	@Before
    public void setUp() {
    	
		 cardIssuer = new CardIssuer("SomeName", 4);
		 amountOwed = new BigDecimal(20);
         payWithDebit = new PayWithDebit(cardIssuer,amountOwed);
         card = new Card("Visa",  "1234567890123456", "SomeName", "111");
         cardData = card.new CardSwipeData();
         
    }
	
	@Test
	public void testPayWithDebitConstructor() {
//		CardIssuer issuer = new CardIssuer("SomeName", 4);
//		BigDecimal owed = new BigDecimal(20);
//		 assertEquals(issuer. ),payWithDebit.getCardIssuer().toString());
//		 assertEquals(owed.toString(),payWithDebit.getAmountOwed().toString());
    }
 
	@Test
	public void testSysReady() {
	payWithDebit.sysReady();
	boolean expected = true;
	assertEquals(expected,payWithDebit.isSysReadyForPayment());
}

	@Test
	public void testpromptForSignature() {
	String signature = "signature";
	payWithDebit.promptForSignature(signature);
	assertEquals(signature,payWithDebit.getCustomerSignature());
}
	@Test

	public void testBankAuthentication() {
	payWithDebit.bankAuthentication(cardData);
	
}

	@Test
	public void testPayViaDebitSwipe() throws IOException {
	
	
}
	@Test
	public void testPayViaDebitSwipeListener() throws IOException {
	
	
}
	
}
