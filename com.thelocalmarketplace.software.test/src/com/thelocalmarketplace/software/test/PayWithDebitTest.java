package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.jjjwelectronics.card.CardReaderBronze;
import com.jjjwelectronics.card.CardReaderGold;
import com.jjjwelectronics.card.CardReaderListener;
import com.tdc.coin.CoinSlot;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.PayWithDebit;

import powerutility.PowerGrid;

public class PayWithDebitTest {
	CardIssuer cardIssuer;
	BigDecimal amountOwed;
    PayWithDebit payWithDebit;
    Card card;
    Card.CardSwipeData cardData;
    CardReaderBronze cardReader;
    CardReaderGold cardReaderGold;
    private static int swipeCount;
    private static boolean dataHasBeenRead;
    
	
  
	@Before
    public void setUp() {
		
		 cardIssuer = new CardIssuer("SomeName", 4);
		 amountOwed = new BigDecimal(20);
         payWithDebit = new PayWithDebit(cardIssuer,amountOwed);
         card = new Card("Visa",  "1234567890123456", "SomeName", "111");
         cardData = card.new CardSwipeData();
         PowerGrid power = PowerGrid.instance();
         cardReader = new CardReaderBronze();
         cardReader.plugIn(power);
         cardReader.turnOn();
         cardReaderGold = new CardReaderGold();
         cardReaderGold.plugIn(power);
         cardReaderGold.turnOn();
         Calendar expiryDate = new Calendar.Builder().setDate(2028, Calendar.SEPTEMBER, 1).build();
 		 cardIssuer.addCardData("1234567890123456", "SomeName", expiryDate, "111",  1000);   
 		 swipeCount = 0;
 		 dataHasBeenRead = false;
	}
	
	@After
	public void tearDown() {
		swipeCount = 0;
		 dataHasBeenRead = false;
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
		boolean expected = true;
		boolean actual = payWithDebit.bankAuthentication(cardData);
		assertEquals(expected,actual);
	
}
	
	@Test
	
	public void testCardBlockedAfterAuthentication() {
		
	}

	@Test
	public void testPayViaDebitSwipe() throws IOException {
		BigDecimal expectedAmount = new BigDecimal(0);
		payWithDebit.sysReady();
		payWithDebit.payViaDebitSwipe(card, "signature",cardReader);
		assertEquals(expectedAmount,payWithDebit.getAmountOwed());
	
	
}
	@Test
	public void testPayViaDebitSwipeListeners() throws IOException {
		BigDecimal expectedAmount = new BigDecimal(0);
		payWithDebit.sysReady();
		cardReader.register(new StubCardReader());
		payWithDebit.payViaDebitSwipe(card, "signature",cardReader);
		assertEquals(expectedAmount,payWithDebit.getAmountOwed());
		assertEquals(1,swipeCount);
		assertEquals(true,dataHasBeenRead);
	
}
	
	
	
	static class StubCardReader implements CardReaderListener{

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
			swipeCount++;
			
		}

		@Override
		public void theDataFromACardHasBeenRead(CardData data) {
			dataHasBeenRead = true;
			
		}
		
	}
	
}
