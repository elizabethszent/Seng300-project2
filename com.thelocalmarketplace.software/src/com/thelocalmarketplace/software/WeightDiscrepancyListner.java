package com.thelocalmarketplace.software;

import com.jjjwelectronics.IDeviceListener;

public interface WeightDiscrepancyListner extends IDeviceListener{
	
	public void WeightDiscrancyOccurs(); 
	
	public void WeightDiscrancyResolved();
	public void addOwnBagsSelected();
	public void addOwnBagDeselected();
}
