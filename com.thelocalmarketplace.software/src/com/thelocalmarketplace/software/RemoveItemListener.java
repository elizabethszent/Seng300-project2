package com.thelocalmarketplace.software;

import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Item;
import com.thelocalmarketplace.hardware.Product;

public interface RemoveItemListener extends IDeviceListener
{
	public void ItemHasBeenRemoved(Item item);
}
