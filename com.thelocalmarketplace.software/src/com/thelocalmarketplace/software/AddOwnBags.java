// Jincheng Li UCID: 30172907
package com.thelocalmarketplace.software;

import java.math.BigInteger;
import java.util.List;

import com.jjjwelectronics.AbstractDevice;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;

import powerutility.NoPowerException;

/**
 * 
 */
public class AddOwnBags extends AbstractDevice<WeightDiscrepancyListner> implements ElectronicScaleListener{
	Mass bagWeight;
	boolean addOwnBagsSelection;
	boolean	testSentinel;
	Mass bagLimit = new Mass(BigInteger.valueOf(500 * Mass.MICROGRAMS_PER_GRAM)); // limit for the bag is 500g
	public AddOwnBags(Mass bagWeight, boolean addOwnBagsSelection, WeightDiscrepancyListner wListner, AbstractElectronicScale listner) {
		this.addOwnBagsSelection = addOwnBagsSelection;
		if(addOwnBagsSelection) {
		// Initializing the bag weight, if the add own bags is selected.
		this.bagWeight = bagWeight;
		this.bagWeightOverloaded();
		this.userChoice();
		}else {
			this.bagWeight = Mass.ZERO;
		}
		// register the class into listener 
		listner.register(this);
	
	}
	// See if the bag weight is out of the allowable range we set
	public void bagWeightOverloaded() {
		if (bagWeight.compareTo(bagLimit)>0) {
			for(WeightDiscrepancyListner l : listeners()){
				l.WeightDiscrancyOccurs();
			}
		}
	}
	
	
	// See if the user chooses the add own bag selection
	public void userChoice() {
		for(WeightDiscrepancyListner l : listeners()) {
			if (addOwnBagsSelection==false){
			l.addOwnBagDeselected();
			boolean testSentienel = false;
			
		}else{
			l.addOwnBagsSelected();
			l.WeightDiscrancyResolved();
			boolean testSentienel = true;
			}
		}
	}
		
	public boolean getTestSentinel() {
		return testSentinel;
		
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
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		// TODO Auto-generated method stub
		
	}
	
}
