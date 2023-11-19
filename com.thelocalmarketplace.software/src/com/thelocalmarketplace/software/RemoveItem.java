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

/**
 * @author Elizabeth Szentmiklossy 	UCID: 30165216
 * @author Jonathan Mulyk 			UCID: 30093143
 * 
 */


/**
 * Handles removing items from an order
 */
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
	
	/**
	 * Constructor to create an instance of remove item
	 * 
	 * @param order - the items in the order, which should be in the bagging area
	 * @param discrepancy - keeps track of weight discrepancy
	 * @param blocker - instance allowing blocking customer interaction from a checkout station
	 */
	public RemoveItem(ArrayList<Item> order, WeightDiscrepancy discrepancy, ActionBlocker blocker)
	{
		this.order = order;
		this.discrepancy = discrepancy;
		this.actionBlocker = blocker;
	}

	
	/**
	 * Notify listeners that an item has been removed from the order
	 * 
	 * @param item - the item that has been removed from the order
	 */
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
	
	/**
	 * Removes an item from an order 
	 * 
	 * @param itemToRemove - the item to remove from the order
	 * @param order - the order that the item is to be removed from
	 */
	public void removeItemFromOrder(Item itemToRemove, ArrayList<Item> order)
	{
		if(itemToRemove == null || order == null)
		{
			throw new NullPointerException();
		}
		
		if(order.size() <= 0)
		{
			throw new OrderException();
		}
		
		//block customer interaction
		actionBlocker.blockInteraction();
		
		//check if the item is in the order
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
		}
		
		//remove item from the order
		order.remove(itemToRemove);

		//update expected weight here 

		
		discrepancy.expectedWeight = new Mass(discrepancy.expectedWeight.inGrams().subtract(itemToRemove.getMass().inGrams()));
		
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
		
		//unblock customer interaction
		actionBlocker.unblockInteraction();
	}

	
	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) 
	{
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) 
	{
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) 
	{
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) 
	{
	}

	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) 
	{
	}

	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) 
	{
	}

	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) 
	{
	}

}
