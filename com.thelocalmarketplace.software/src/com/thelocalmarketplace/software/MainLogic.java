//Elizabeth Szentmiklossy UCID: 30165216
//Justine Mangaliman UCID: 30164741
// Arthur Huan 30197354

package com.thelocalmarketplace.software;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.IComponent;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainLogic {
    public AbstractSelfCheckoutStation checkoutStation;
    private ProductDatabases productDatabase;
    public Session currentSession;
    private ArrayList<Object> hardwareList;


    /**
     * Install software on checkout station
     *
     * @param station
     *              The self-checkout machine on which to install the software
     *
     */
    public static MainLogic installOn(AbstractSelfCheckoutStation station) {
        return new MainLogic(station);
    }

    /**
     * Constructor
     */
    public MainLogic(AbstractSelfCheckoutStation station) {
        checkoutStation = station;
        hardwareList = new ArrayList<>();
        for (Object field : station.getClass().getDeclaredFields()) {
            if (field instanceof IDevice || field instanceof IComponent) {
                hardwareList.add(field);
            }
        }
    }
}
