package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.software.HandleBulkyItem;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scale.ElectronicScaleBronze;
import com.jjjwelectronics.scale.ElectronicScaleGold;
import com.jjjwelectronics.scale.ElectronicScaleSilver;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import powerutility.PowerGrid; 


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HandleBulkyItemTest {
	private AbstractSelfCheckoutStation checkoutStation;
    private SelfCheckoutStationBronze bronzeStation;
    private HandleBulkyItem handleBulkyItem;
    private WeightDiscrepancy weightDiscrepancy;
    private Item bulkyItem;
    private PowerGrid grid;
    IDeviceListener listener;
    WeightDiscrepancy discrepancy;

    @Before
    public void setUp() {
    	checkoutStation = new SelfCheckoutStationBronze();
        grid = PowerGrid.instance();
        bronzeStation.plugIn(grid);
        bronzeStation.turnOn();

        Numeral[] numerals = new Numeral[]{Numeral.valueOf((byte) 2)};
        handleBulkyItem = new HandleBulkyItem(checkoutStation, listener);
        Barcode barcode = new Barcode(numerals);
        BarcodedItem item;
    }

    @Test
    public void testRequestNoBaggingDisablesDevices() {
        handleBulkyItem.requestNoBagging(false);

        assertTrue("Main scanner should be disabled", bronzeStation.mainScanner.isDisabled());
        assertTrue("Handheld scanner should be disabled", bronzeStation.handheldScanner.isDisabled());
    }
    
    @Test
    public void testRequestNoBaggingEnabled() {
    	boolean bagItem = true;
        handleBulkyItem.requestNoBagging(bagItem);
        assertFalse("Main scanner should still be enabled after no bagging request", bronzeStation.mainScanner.isDisabled());
        assertFalse("Handheld scanner should still be enabled after no bagging request", bronzeStation.handheldScanner.isDisabled());
		assertEquals("Weight discrepancy should be triggered", discrepancy.CompareWeight(),true);
    }
    
    @Test
    public void testNotifyAttendantEnablesDevices() {
    	boolean attendantApproval = true;
    	handleBulkyItem.notifyAttendant(attendantApproval);
    	assertFalse("Main scanner should be enabled after approval", bronzeStation.mainScanner.isDisabled());
        assertFalse("Handheld scanner should be enabled after approval", bronzeStation.handheldScanner.isDisabled());
    }
    
    @Test
    public void testNotifyAttendantReturnsFalse() {
    	boolean attendantApproval = false;
    	handleBulkyItem.notifyAttendant(attendantApproval);
    	assertFalse("Main scanner should be enabled after approval", bronzeStation.mainScanner.isDisabled());
        assertFalse("Handheld scanner should be enabled after approval", bronzeStation.handheldScanner.isDisabled());
    }
    
}

