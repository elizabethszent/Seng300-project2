package com.thelocalmarketplace.software;

import java.util.HashMap;
import java.util.Map;

import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;

/**
 * A non-final, non-static version of com.thelocalmarketplace.hardware.external.ProductDatabases.
 * This allows users to be able to use the system with populated databases.
 */
public class StoreDatabases {
    /**
     * Instances of this class are very much needed.
     */
    public StoreDatabases() {}
        /**
         * The known PLU-coded products, indexed by PLU code.
         */
        public Map<PriceLookUpCode, PLUCodedProduct> PLU_PRODUCT_DATABASE = new HashMap<>();

        /**
         * The known barcoded products, indexed by barcode.
         */
        public Map<Barcode, BarcodedProduct> BARCODED_PRODUCT_DATABASE = new HashMap<>();

        /**
         * A count of the items of the given product that are known to exist in the
         * store. Of course, this does not account for stolen items or items that were
         * not correctly recorded, but it helps management to track inventory.
         */
        public Map<Product, Integer> INVENTORY = new HashMap<>();
}
