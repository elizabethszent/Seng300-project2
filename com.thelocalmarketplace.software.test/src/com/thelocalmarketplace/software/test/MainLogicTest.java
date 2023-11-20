package com.thelocalmarketplace.software.test;

import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.software.MainLogic;
import com.thelocalmarketplace.software.StoreDatabases;
import org.junit.Before;
import org.junit.Test;
import powerutility.PowerGrid;

public class MainLogicTest {
    private SelfCheckoutStationBronze checkoutStation;
    private StoreDatabases productDatabase;
    public PowerGrid grid = PowerGrid.instance();

    @Before
    public void setUp() {
        MainLogic.installOn(checkoutStation, productDatabase);
        checkoutStation.plugIn(grid);
        checkoutStation.turnOn();
    }

    @Test
    public void testStation() {

    }
}
