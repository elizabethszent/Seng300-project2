package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.ElectronicScaleBronze;
import com.jjjwelectronics.scale.ElectronicScaleGold;
import com.jjjwelectronics.scale.ElectronicScaleSilver;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.software.ActionBlocker;
import com.thelocalmarketplace.software.RemoveItem;
import com.thelocalmarketplace.software.WeightDiscrepancy;
import com.thelocalmarketplace.software.exceptions.OrderException;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Mass.MassDifference;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.OverloadedDevice;

import powerutility.PowerGrid;

public class RemoveItemTest 
{
	ElectronicScaleGold goldScale;
	ElectronicScaleSilver silverScale;
	ElectronicScaleBronze bronzeScale;
	
	PLUCodedItem orange;
//	PriceLookUpCode orangePLUCode;
	BarcodedItem shampoo;

    HashMap<Item, Integer> cart = new HashMap<Item, Integer>();
    RemoveItem tempRemoveItemInstance;
    
    int cartSize;
    
    
	ArrayList<Item> order;
	WeightDiscrepancy discrepancy;
	ActionBlocker actionBlocker;
	int orderSize;
	Mass massOfOrder;
	
	/*
	ElectronicScaleGold listner = new ElectronicScaleGold();
	WeightDiscrepancy discrepancy = new WeightDiscrepancy(Mass.ZERO, listner);
	CoinSlot coin_slot = new CoinSlot();
	ArrayList< java.util.Map.Entry < BigDecimal,AbstractCoinDispenser>> amount = new ArrayList < java.util.Map.Entry < BigDecimal,AbstractCoinDispenser>> ();
	PowerGrid grid = PowerGrid.instance();
	*/
	
	@Before
	public void setup()
	{
		//create scale objects and make sure they're functional
		this.goldScale = new ElectronicScaleGold();
		this.silverScale = new ElectronicScaleSilver();
		this.bronzeScale = new ElectronicScaleBronze();

		this.goldScale.plugIn(PowerGrid.instance());
		this.silverScale.plugIn(PowerGrid.instance());
		this.bronzeScale.plugIn(PowerGrid.instance());
		
		this.goldScale.turnOn();
		this.silverScale.turnOn();
		this.bronzeScale.turnOn();
		
		this.goldScale.enable();
		this.silverScale.enable();
		this.bronzeScale.enable();
		
		Mass orangeMass = new Mass(200000000);
		PriceLookUpCode orangePLUCode = new PriceLookUpCode("0123");
		this.orange = new PLUCodedItem(orangePLUCode, orangeMass);
		
		Numeral[] shampooBarcodeCode = {Numeral.one, Numeral.two, Numeral.one};
		Barcode shampooBarcode = new Barcode(shampooBarcodeCode);
		Mass shampooMass = new Mass(450000000);
		this.shampoo = new BarcodedItem(shampooBarcode, shampooMass);
		
		cart.put(this.orange, 1);
		cart.put(this.shampoo, 1);
		
		this.cartSize = cart.size();
		
		this.order = new ArrayList<Item>();
		this.order.add(orange);
		this.order.add(shampoo);
		this.orderSize = this.order.size();
		
		this.massOfOrder = orange.getMass().sum(shampoo.getMass());
		
		this.actionBlocker = new ActionBlocker();
		this.discrepancy = new WeightDiscrepancy(this.massOfOrder, bronzeScale);
		this.tempRemoveItemInstance = new RemoveItem(this.order, this.discrepancy, this.actionBlocker);
//		this.tempRemoveItemInstance = new RemoveItem(this.order, this.actionBlocker);
		this.tempRemoveItemInstance.register(discrepancy);
		
		bronzeScale.addAnItem(orange);
		bronzeScale.addAnItem(shampoo);
		silverScale.addAnItem(orange);
		silverScale.addAnItem(shampoo);
		goldScale.addAnItem(orange);
		goldScale.addAnItem(shampoo);
	} 

	@Test
	public void TestScaleConfiguration()
	{
		boolean goldScalePluggedIn = this.goldScale.isPluggedIn();
		boolean silverScalePluggedIn = this.silverScale.isPluggedIn();
		boolean bronzeScalePluggedIn = this.bronzeScale.isPluggedIn();
		assertTrue(goldScalePluggedIn);
		assertTrue(silverScalePluggedIn);
		assertTrue(bronzeScalePluggedIn);
		
		boolean goldScaleTurnedOn = this.goldScale.isPoweredUp();
		boolean silverScaleTurnedOn = this.silverScale.isPoweredUp();
		boolean bronzeScaleTurnedOn = this.bronzeScale.isPoweredUp();
		assertTrue(goldScaleTurnedOn);
		assertTrue(silverScaleTurnedOn);
		assertTrue(bronzeScaleTurnedOn);
		
		boolean goldScaleDisabled = this.goldScale.isDisabled();
		boolean silverScaleDisabled = this.silverScale.isDisabled();
		boolean bronzeScaleDisabled = this.bronzeScale.isDisabled();
		assertFalse(goldScaleDisabled);
		assertFalse(silverScaleDisabled);
		assertFalse(bronzeScaleDisabled);
	}

	@Test
	public void TestRemovePLUCodedItem()
	{
		tempRemoveItemInstance.removeItemFromOrder(orange, order);
		assertEquals(this.orderSize - 1, this.order.size());
	}

	@Test
	public void TestRemoveBarcodedItem()
	{
		tempRemoveItemInstance.removeItemFromOrder(shampoo, order);
		assertEquals(this.orderSize - 1, this.order.size());
	}

	
	@Test
	public void TestRemoveMultipleItems()
	{
		tempRemoveItemInstance.removeItemFromOrder(orange, order);
		tempRemoveItemInstance.removeItemFromOrder(shampoo, order);
		assertEquals(this.orderSize - 2, this.order.size());
	}
	
	
	@Test (expected = OrderException.class)
	public void TestRemoveItemNotInOrder()
	{
		Mass appleMass = new Mass(220000000);
		PriceLookUpCode applePLUCode = new PriceLookUpCode("0132");
		PLUCodedItem apple = new PLUCodedItem(applePLUCode, appleMass);

		tempRemoveItemInstance.removeItemFromOrder(apple, order);
		assertEquals(this.orderSize - 1, 0);
	}

	@Test (expected = OrderException.class)
	public void TestRemoveFromEmptyCart()
	{
		Mass appleMass = new Mass(220000000);
		PriceLookUpCode applePLUCode = new PriceLookUpCode("0132");
		PLUCodedItem apple = new PLUCodedItem(applePLUCode, appleMass);

		tempRemoveItemInstance.removeItemFromOrder(orange, order);
		tempRemoveItemInstance.removeItemFromOrder(shampoo, order);
		tempRemoveItemInstance.removeItemFromOrder(apple, order);
	}
	
	
	@Test (expected = NullPointerException.class)
	public void TestRemoveNullItem()
	{
		Item item = null;
		tempRemoveItemInstance.removeItemFromOrder(item, order);
	}
	

}
