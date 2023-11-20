package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scale.ElectronicScaleGold;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.software.AddOwnBags;
import com.thelocalmarketplace.software.WeightDiscrepancy;

import powerutility.PowerGrid;

public class AddOwnBagsTest {
	PowerGrid grid = PowerGrid.instance();
//	// 200g for the weight of bag
//	Mass bagWeight1 = new Mass(BigInteger.valueOf(200 * Mass.MICROGRAMS_PER_GRAM));
//	Mass expectedWeight1 = new Mass(BigInteger.valueOf(800 * Mass.MICROGRAMS_PER_GRAM));
//	boolean addOwnBagsSelection1 = true;
//	// Create an instance of WeightDiscrepancy with an expected weight of one gram and the electronic scale listener.
//	ElectronicScaleGold listner1 = new ElectronicScaleGold();
//	AddOwnBags bag1 = new AddOwnBags(bagWeight1, expectedWeight1, addOwnBagsSelection1, listner1);
	@Test
	public void testTheMassOnTheScaleHasChanged() {
		// 200g for the weight of bag
		Mass bagWeight1 = new Mass(BigInteger.valueOf(200 * Mass.MICROGRAMS_PER_GRAM));
		Mass expectedWeight1 = new Mass(BigInteger.valueOf(800 * Mass.MICROGRAMS_PER_GRAM));
		boolean addOwnBagsSelection1 = true;
		// Create an instance of WeightDiscrepancy with an expected weight of one gram and the electronic scale listener.
		ElectronicScaleGold listner1 = new ElectronicScaleGold();
		AddOwnBags bag1 = new AddOwnBags(bagWeight1, expectedWeight1, addOwnBagsSelection1, listner1);
		bag1.userChoice();
		
		assertEquals(bag1.getTestSentinel(), true);
	}
}
