package com.thelocalmarketplace.software;

import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Item;
import com.thelocalmarketplace.hardware.Product;

public interface ItemControllerListener extends IDeviceListener{
	
	public void ItemHasBeenAdded(Item item);
	public void ItemHasBeenRemoved(Item item, int amount);
}
	


