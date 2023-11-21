//Elizabeth Szentmiklossy UCID: 30165216
//Justine Mangaliman UCID: 30164741
//Enzo Mutiso UCID: 30182555
//Abdelrahman Mohamed UCID: 30162037
//Mohammad Mustafa Mehtab UCID: 30189394
package com.thelocalmarketplace.software.test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scale.ElectronicScaleBronze;
import com.jjjwelectronics.scale.ElectronicScaleGold;
import com.jjjwelectronics.scale.ElectronicScaleSilver;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.coin.AbstractCoinDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenserBronze;
import com.tdc.coin.CoinSlot;
import com.thelocalmarketplace.hardware.CoinTray;
import com.thelocalmarketplace.software.*;

import powerutility.PowerGrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains JUnit test cases to verify the functionality of the
 * PayviaCoin class.
 * 
 * @author Elizabeth Szentmiklossy (UCID: 30165216)
 * 
 */
public class PayviaCoinTest {
	ElectronicScaleGold listner = new ElectronicScaleGold();
	WeightDiscrepancy discrepancy = new WeightDiscrepancy(Mass.ZERO, listner);
	CoinSlot coin_slot = new CoinSlot();
	ArrayList<java.util.Map.Entry<BigDecimal, AbstractCoinDispenser>> amount = new ArrayList<java.util.Map.Entry<BigDecimal, AbstractCoinDispenser>>();
	PowerGrid grid = PowerGrid.instance();
	CoinTray sink = new CoinTray(10); 

	/**
	 * Test case to verify adding coins and making payments with the PayviaCoin
	 * class.
	 * @throws NoCashAvailableException 
	 */
	@Test
	public void AddCoins() throws NoCashAvailableException {

		// The starting amount paid
		PayviaCoin paymentHandeler = new PayviaCoin(BigDecimal.ZERO, null, discrepancy, coin_slot, amount);
		// The amount owed
		PayviaCoin payment = new PayviaCoin(BigDecimal.valueOf(3), null, discrepancy, coin_slot, amount);

		// The amount that is being inserted each time
		Coin tray = new Coin(BigDecimal.ONE);

		// Test the MakePayment method with various scenarios.
		assertEquals(paymentHandeler.MakePayment(tray), false);
		// One DOLLAR PAID
		assertEquals(payment.MakePayment(tray), true);
		// $2 PAID
		assertEquals(payment.MakePayment(tray), true);
		// $3 Paid. Total paid so makepayment should return false
		assertEquals(payment.MakePayment(tray), false);
		// check for validity
		assertEquals(payment.MakePayment(tray), false);
	}

	/**
	 * Test case to check what happens when not enough change
	 */
	/*
	 * @Test public void NotEnoughChnage() { PayviaCoin amount_owed = new
	 * PayviaCoin(BigDecimal.valueOf(170.85), null, discrepancy, coin_slot, amount);
	 * 
	 * Coin tray = new Coin(BigDecimal.valueOf(180));
	 * 
	 * //if(change.compareTo(amount_owed)) {
	 * assertThrows(NoCashAvailableException.class,() ->
	 * amount_owed.MakePayment(tray)); //}
	 */ //}
	
	

	@Test
	public void NotEnoughChange() {
	    // Mock objects or set up dependencies as needed for the test
	    CoinSlot coinSlot = new CoinSlot();
	    WeightDiscrepancy discrepancy = new WeightDiscrepancy(null, listner);

	    // Assuming CoinTray requires a positive capacity, set it to a value that ensures overflow
	    int trayCapacity = 1; // Set this value based on your test scenario
	    CoinTray tray = new CoinTray(trayCapacity);

	    // Create PayviaCoin instance with the expected amount owed, tray, discrepancy, coin slot, and dispensers
	    PayviaCoin amountOwed = new PayviaCoin(BigDecimal.valueOf(170.85), tray, discrepancy, coinSlot, amount);

	    // Attempt to make a payment with a coin that exceeds the amount owed
	    Coin coin = new Coin(BigDecimal.valueOf(180));
	    amountOwed.MakePayment(coin);
	    
	    // Assert that a NoCashAvailableException is thrown
	    assertThrows(NoCashAvailableException.class, () -> amountOwed.GiveChange());
	}



	 @Test
	    public void Change() throws NoCashAvailableException, CashOverloadException, DisabledException {
	       double Payed = 180;
	       double Owed = 171;
	       double Change = Payed - Owed;
		 
		    

	        WeightDiscrepancy discrepancy = new WeightDiscrepancy(Mass.ZERO, new ElectronicScaleGold());
	        CoinSlot coinSlot = null;
			PayviaCoin payviaCoin = new PayviaCoin(BigDecimal.valueOf(171), sink, discrepancy, coinSlot, amount);

	        // Insert a coin to trigger the GiveChange method
	        Coin coin = new Coin(BigDecimal.valueOf(180));
	        
	        for(java.util.Map.Entry< BigDecimal,AbstractCoinDispenser> i:amount) {
	        	for(int j=0; j<5; j++) {
				BigDecimal exactchange = i.getKey();
				AbstractCoinDispenser value = i.getValue();
	            Coin loadingcoin = new Coin(exactchange);
	            value.load(loadingcoin);
	        }
	        }
	        
	        payviaCoin.MakePayment(coin);

	        // Call the GiveChange method
	        payviaCoin.GiveChange();

	        // Verify that the collectCoins method on the mock CoinTray is called
	        BigDecimal sum = BigDecimal.ZERO;
	        for(Coin i:sink.collectCoins()) {
	        	sum = sum.add(i.getValue());	
	        }
	        sum.doubleValue();
	        assertEquals(sum.doubleValue(), Change, 0.0000000111);
	    }

	/**
	 * Test case to verify change dispenced
	 */
	/*
	 * @Test public void Change() {
	 * 
	 * PayviaCoin amount_owed = new PayviaCoin(BigDecimal.valueOf(170.85), null,
	 * discrepancy, coin_slot, amount); Coin tray = new
	 * Coin(BigDecimal.valueOf(180));
	 * 
	 * }
	 */
	/**
	 * Sets up the test fixture. Called before every test case method.
	 */
	@Before
	public void SetUp() {
	
		coin_slot.connect(grid);
		coin_slot.activate();
	

		// Set the default currency to Canadian dollars for testing.
		Coin.DEFAULT_CURRENCY = Currency.getInstance(Locale.CANADA);

	    // Use the existing 'amount' list to create the 'dispensers' list
	    amount = new ArrayList<>();
	    
		amount.add(new java.util.AbstractMap.SimpleEntry<BigDecimal, AbstractCoinDispenser>(BigDecimal.valueOf(100),
				new CoinDispenserBronze(10)));
		amount.add(new java.util.AbstractMap.SimpleEntry<BigDecimal, AbstractCoinDispenser>(BigDecimal.valueOf(50),
				new CoinDispenserBronze(10)));
		amount.add(new java.util.AbstractMap.SimpleEntry<BigDecimal, AbstractCoinDispenser>(BigDecimal.valueOf(20),
				new CoinDispenserBronze(10)));
		amount.add(new java.util.AbstractMap.SimpleEntry<BigDecimal, AbstractCoinDispenser>(BigDecimal.valueOf(10),
				new CoinDispenserBronze(10)));
		amount.add(new java.util.AbstractMap.SimpleEntry<BigDecimal, AbstractCoinDispenser>(BigDecimal.valueOf(5),
				new CoinDispenserBronze(10)));
		amount.add(new java.util.AbstractMap.SimpleEntry<BigDecimal, AbstractCoinDispenser>(BigDecimal.valueOf(2),
				new CoinDispenserBronze(10)));
		amount.add(new java.util.AbstractMap.SimpleEntry<BigDecimal, AbstractCoinDispenser>(BigDecimal.valueOf(1),
				new CoinDispenserBronze(10)));
		amount.add(new java.util.AbstractMap.SimpleEntry<BigDecimal, AbstractCoinDispenser>(BigDecimal.valueOf(.25),
				new CoinDispenserBronze(10)));
		amount.add(new java.util.AbstractMap.SimpleEntry<BigDecimal, AbstractCoinDispenser>(BigDecimal.valueOf(.10),
				new CoinDispenserBronze(10)));
		amount.add(new java.util.AbstractMap.SimpleEntry<BigDecimal, AbstractCoinDispenser>(BigDecimal.valueOf(.05),
				new CoinDispenserBronze(10)));
	
		for(java.util.Map.Entry< BigDecimal,AbstractCoinDispenser> i:amount) {
			i.getValue().connect(grid);
			i.getValue().activate();
			i.getValue().sink = sink;
			
			
		}
	}

}
