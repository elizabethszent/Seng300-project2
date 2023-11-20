package com.thelocalmarketplace.software.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import org.junit.*;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.Sink;
import com.tdc.banknote.AbstractBanknoteDispenser;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispensationSlot;
import com.tdc.banknote.BanknoteDispenserBronze;
import com.tdc.banknote.BanknoteInsertionSlot;
import com.thelocalmarketplace.software.PayViaBanknote;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.NoPowerException;
import powerutility.PowerGrid;

public class PayViaBanknoteTest {
	private PayViaBanknote payViaBanknote;
	private BigDecimal amountOwed;
	private BanknoteDispensationSlot dispensationSlot;
	private BanknoteInsertionSlot insertionSlot;
	private List<Map.Entry<BigDecimal, AbstractBanknoteDispenser>> dispenserList;
	private PowerGrid grid;


	
	/**
	 * Initializes objects before each test case.
	 */
	@Before
	public void setup() {
		BigDecimal amountOwed = BigDecimal.valueOf(10);
		dispensationSlot = new BanknoteDispensationSlot();
		insertionSlot = new BanknoteInsertionSlot();
		dispenserList = new ArrayList<>();
		grid = PowerGrid.instance();
		BigDecimal amountInserted = BigDecimal.ZERO;
		AbstractBanknoteDispenser dispenser = new BanknoteDispenserBronze();  //Testing with Bronze tier dispenser (Checkoutstation)
		dispenserList.add(Map.entry(BigDecimal.TEN, dispenser));   // Adds dispenser with denomination
		dispensationSlot.activate();
		insertionSlot.activate();
		dispenser.activate();
		dispenser.connect(grid);
		
		payViaBanknote = new PayViaBanknote(amountOwed, dispensationSlot, insertionSlot, dispenserList);
		
	}
		
	
	//Test for when payment is complete
	@Test
	public void testPaymentSuccesful() {
		Banknote banknote = new Banknote( Currency.getInstance("CAD"),BigDecimal.TEN); 
        assertTrue(payViaBanknote.makePayment(banknote));		
	}
	
	// Test for when payment is not complete (Not paid in full)
	@Test
	public void testPaymentUnsuccesful() {
		Banknote banknote = new Banknote( Currency.getInstance("CAD"),BigDecimal.valueOf(5)); 
        assertEquals(false,payViaBanknote.makePayment(banknote));	//makepayment() will return false as payment is not complete	
	}
	
	// Testing return Change
	@Test
	public void testReturnChange() throws CashOverloadException, DisabledException{		
		insertionSlot.connect(grid);
		insertionSlot.enable();
		Banknote banknote = new Banknote( Currency.getInstance("CAD"),BigDecimal.valueOf(20)); 
		payViaBanknote.makePayment(banknote);
		payViaBanknote.returnChange();		
	}
	
	
	

}
