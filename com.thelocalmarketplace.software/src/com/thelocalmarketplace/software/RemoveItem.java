package com.thelocalmarketplace.software;

import java.util.ArrayList;
import java.util.HashMap;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.software.exceptions.OrderException;
import com.jjjwelectronics.AbstractDevice;



import ca.ucalgary.seng300.simulation.SimulationException;

public class RemoveItem extends AbstractDevice<RemoveItemListener> implements ElectronicScaleListener
{
	
	private ArrayList<Item> order;
	private WeightDiscrepancy discrepancy;
	private ActionBlocker actionBlocker;
	
	public RemoveItem(ArrayList<Item> order, ActionBlocker blocker)
	{
		this.order = order;
		this.actionBlocker = blocker;
	}
	
	public RemoveItem(ArrayList<Item> order, WeightDiscrepancy discrepancy, ActionBlocker blocker)
	{
		this.order = order;
		this.discrepancy = discrepancy;
		this.actionBlocker = blocker;
	}


	public void itemHasBeenRemoved(Item item)
	{
		for(RemoveItemListener l : listeners())
		{
			l.ItemHasBeenRemoved(item);
		}
	}
	
	/**
	 * Removes items from the cart. If the amount exceeds the number of items in the cart, then it will instead 
	 * remove all the items from the cart
	 * 
	 * @param item - the item to be removed from the cart
	 * @param amount - amount of 'item' that will be removed from the cart
	 * @param cart - the cart containing items
	 */
	public void removeItemFromCart(Item item, Integer amount, HashMap<Item, Integer> cart)
	{
		//if the amount is invalid, then do nothing and do not continue with the function
		if(amount <= 0)
		{
			return;
		}

		if(cart.size() <= 0)
		{
//			throw new SimulationException();
		}
		
		//Assume that the customer can only select valid items to remove
		if(cart.get(item) == null)
		{
			// item not found in cart
		}
		
		actionBlocker.blockInteraction();
		
		int itemAmount = cart.get(item);
		int amountAfterRemoving = amount - itemAmount;

		//if it's the only item in the cart, remove all instances of that item (case of equal to 1)
		//if it's 0 or less, then the amount to remove exceeded the amount in the cart, so we can still remove
		//that item from the cart
		if(amountAfterRemoving <= 1)
		{
			cart.remove(item);
		}
		//if there are more than one of that item in the cart, decrement the amount by 1
		else
		{
			cart.put(item, amountAfterRemoving);
		}
		
		//expectedWeight -= item.getmass();
		
		
		//Expect the theMassOnTheScaleHasChanged to handle the new expected weight accordingly 
		// for when the user removes the weight from the scale
	}

	public void removeItemFromOrder(Item itemToRemove, ArrayList<Item> order)
	{
		if(itemToRemove == null)
		{
			throw new NullPointerException();
		}
		
		if(order.size() <= 0)
		{
			throw new OrderException();
		}

		actionBlocker.blockInteraction();
		
		boolean itemFoundInOrder = false;
		for(Item item : order)
		{
			if(item == itemToRemove)
			{
				itemFoundInOrder = true;
				break;
			}
		}
		
		if(!itemFoundInOrder)
		{
			throw new OrderException();
			//item not found exception
		}
		
		order.remove(itemToRemove);
		actionBlocker.unblockInteraction();
		
//		discrepancy.expectedWeight = new Mass(discrepancy.expectedWeight.inGrams().subtract(itemToRemove.getMass().inGrams()));
		
		/*
		if(discrepancy.CompareWeight())
		{
			//enable systems
		}
		else
		{
			//disable systems
		}
		*/
		
		itemHasBeenRemoved(itemToRemove);
		
	}

	
	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		
		//block self checkout station
		//remove item from customer's order
		//signals that removal was successful
		//unblock

		// TODO Auto-generated method stub
		
	}

	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}

}
