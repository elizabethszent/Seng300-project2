//Elizabeth Szentmiklossy UCID: 30165216
//Justine Mangaliman UCID: 30164741
// Arthur Huan 30197354

package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.ProductDatabases;


public class MainLogic {
    public AbstractSelfCheckoutStation checkoutStation;
    private StoreDatabases productDatabase;


    /**
     * Install software on checkout station
     *
     * @param station
     *              The self-checkout machine on which to install the software
     * @param database
     *              The product database, containing three hash maps:
     *                  PriceLookUpCode --> PLUCodedProduct
     *                  Barcode --> BarcodedProduct
     *                  Product --> Integer         * A count of the number of items in inventory
     *
     */
    public static MainLogic installOn(AbstractSelfCheckoutStation station, StoreDatabases database) {
        return new MainLogic(station, database);
    }

    /**
     * Constructor
     */
    public MainLogic(AbstractSelfCheckoutStation station, StoreDatabases database) {
        checkoutStation = station;
        productDatabase = database;
    }
}
