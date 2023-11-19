package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.*;

import ca.ucalgary.seng300.simulation.SimulationException;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleSilver;
import com.jjjwelectronics.scale.ElectronicScaleGold;
import com.jjjwelectronics.scale.ElectronicScaleBronze;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scale.ElectronicScaleListener; 

public class HandleBulkyItem implements IDeviceListener, ElectronicScaleListener {
    private AbstractSelfCheckoutStation station; // the self checkout station
    private WeightDiscrepancy weightDiscrepancy; // handling weight discrepancy
    private Item bulkyItem; // the bulky item that is not being bagged
    private boolean attendantApproval; // whether the attendant approves or not
    private Product product;

    public HandleBulkyItem(AbstractSelfCheckoutStation station, IDeviceListener listener) {
        this.station = station;
        this.weightDiscrepancy = weightDiscrepancy;
        this.bulkyItem = bulkyItem;
        
        station.mainScanner.register(this);
    }


    // This method is called when the customer requests to not bag an item
    // If the customer does not bag the item, disable inputs from being received
    // If the customer does bag the item, handle this as a weight discrepancy
    public boolean requestNoBagging(boolean bagItem) {
        if (!bagItem) {
            disableDevice(station.mainScanner);
            disableDevice(station.handheldScanner);
            notifyAttendant(attendantApproval);
            return true;
        } else {
			weightDiscrepancy.ItemHasBeenAdded(product);
            return false;
        }
    }


    public boolean notifyAttendant(boolean attendantApproval) {
    	// No way to receive attendant feedback yet
    	// Assume approval is given
        attendantApproval = true;
        if (attendantApproval) {
        	fixDiscrepancy();
            enableDevice(station.mainScanner);
            enableDevice(station.handheldScanner);
            return true;
        } else {
        	// Open issue 2: what happens if the attendant denies?
            return false;
        }
    }

    public void fixDiscrepancy() {
        // Get the electronic scale from the self-checkout station
        IElectronicScale baggingAreaScale = station.baggingArea;

        try {
            // Remove the bulky item from the scale
            baggingAreaScale.removeAnItem(bulkyItem);

            // Assuming the scale automatically updates the expected weight and notifies listeners
        } catch (SimulationException e) {
        }
    }
    
    private void disableDevice(IDevice device) {
        if (!device.isDisabled()) {
            device.disable();
        }
    }

    private void enableDevice(IDevice device) {
        if (device.isDisabled()) {
            device.enable();
        }
    }

    @Override
    public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'aDeviceHasBeenEnabled'");
    }
    
    @Override
    public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'aDeviceHasBeenDisabled'");
    }

    @Override
    public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'aDeviceHasBeenTurnedOn'");
    }

    @Override
    public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'aDeviceHasBeenTurnedOff'");
    }


    @Override
    public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'theMassOnTheScaleHasChanged'");
    }


    @Override
    public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'theMassOnTheScaleHasExceededItsLimit'");
    }


    @Override
    public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'theMassOnTheScaleNoLongerExceedsItsLimit'");
    }

}