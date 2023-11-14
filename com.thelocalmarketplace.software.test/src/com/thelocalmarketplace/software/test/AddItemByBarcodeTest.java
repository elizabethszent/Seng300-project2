//Elizabeth Szentmiklossy UCID: 30165216
//Justine Mangaliman UCID: 30164741
//Enzo Mutiso UCID: 30182555
//Abdelrahman Mohamed UCID: 30162037
//Mohammad Mustafa Mehtab UCID: 30189394

package com.thelocalmarketplace.software.test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scale.ElectronicScaleBronze;
import com.jjjwelectronics.scale.ElectronicScaleGold;
import com.jjjwelectronics.scale.ElectronicScaleSilver;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerBronze;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.software.ActionBlocker;
import com.thelocalmarketplace.software.AddItemByBarcode;
import com.thelocalmarketplace.software.WeightDiscrepancy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import powerutility.PowerGrid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * AddItemByBarcodeTest class handles test cases relating to the AddItemByBarcode class.
 *
 * @author Enzo Mutiso UCID: 30182555
 */
public class AddItemByBarcodeTest {

    /**
     * The AddItemByBarcode object to be tested.
     */
    private AddItemByBarcode addItemByBarcode;
    /**
     * The order where products will be added.
     */
    private ArrayList<Product> order;
    /**
     * The barcode of the product to be added.
     */
    private Barcode barcode;
    /**
     * The BarcodedProduct object to be added to the order.
     */
    private BarcodedProduct barcodedProduct;
    /**
     * The expected weight to match with the actual weight.
     */
    private Mass expectedWeight;
    /**
     * The WeightDiscrepancy object for weight comparison.
     */
    private WeightDiscrepancy discrepancy;
    /**
     * The ActionBlocker object to block customer interaction.
     */
    private ActionBlocker blocker;
    /**
     * The ElectronicScale object to get the actual weight.
     */

    /**
     * The database of products.
     */
    private Map<Barcode, BarcodedProduct> database;
    
	private BarcodeScannerBronze barcodescanner;
	private ElectronicScaleBronze electronicScale;
	private BarcodedItem item;


    /**
     * Sets up the test fixture. Called before every test case method.
     */
    @Before
    public void SetUp() {
        database = new HashMap<>();
        order = new ArrayList<>();
        Numeral[] numeralArray = {Numeral.one, Numeral.two, Numeral.three, Numeral.four, Numeral.five, Numeral.six, Numeral.seven};
        barcode = new Barcode(numeralArray);
        electronicScale = new ElectronicScaleBronze();
        barcodedProduct = new BarcodedProduct(barcode, "Sample Product", 100, 50);
        expectedWeight = Mass.ZERO;
        discrepancy = new WeightDiscrepancy(expectedWeight, electronicScale);
        blocker = new ActionBlocker();
        barcodescanner  = new BarcodeScannerBronze();
        item = new BarcodedItem(barcode, expectedWeight);
        addItemByBarcode = new AddItemByBarcode(barcodescanner, order, discrepancy, blocker, electronicScale, database);


        // add product to database
        database.put(barcode, barcodedProduct);

        // try catch to turn everything on
        try {
            this.electronicScale.plugIn(PowerGrid.instance());
            this.electronicScale.turnOn();
            this.electronicScale.enable();
        } catch (Exception e) {
            PowerGrid.instance().forcePowerRestore();
            SetUp();
        }

    }


    /**
     * Tests the functionality of adding a barcode to the order. Verifies if the barcode has been successfully added to the order list.
     *
     */
    @Test
    public void addBarcodeToOrder() {
        StubBarcodeScanner stubBarcodeScanner = new StubBarcodeScanner();
        electronicScale.addAnItem(item); // use stub to simulate weight change
        addItemByBarcode.aBarcodeHasBeenScanned(stubBarcodeScanner, barcode);
        assertEquals(1, addItemByBarcode.getOrder().size());
    }

    /**
     * Tests the scenario when a product is not found in the database. Expects the interaction to be blocked.
     *
     */
    @Test
    public void testProductNotFoundException() {
        Numeral[] numeralTest = {Numeral.one, Numeral.seven};
        Barcode notInDatabaseBarcode = new Barcode(numeralTest);
        StubBarcodeScanner stubBarcodeScanner = new StubBarcodeScanner();
        electronicScale.addAnItem(item); // use stub to simulate weight change
        addItemByBarcode.aBarcodeHasBeenScanned(stubBarcodeScanner, notInDatabaseBarcode);
        Assert.assertTrue(blocker.isInteractionBlocked());
    }

    /**
     * Tests the retrieval of the expected weight. Verifies if the retrieved weight matches the expected weight.
     */
    @Test
    public void testGetExpectedWeight() {
        assertEquals(expectedWeight, addItemByBarcode.getExpectedWeight());

        StubBarcodeScanner stubBarcodeScanner = new StubBarcodeScanner();
        electronicScale.addAnItem(item); // use stub to simulate weight change
        addItemByBarcode.aBarcodeHasBeenScanned(stubBarcodeScanner, barcode);

        assertEquals(new Mass(barcodedProduct.getExpectedWeight()), addItemByBarcode.getExpectedWeight());
    }

    /**
     * Test to check whether proper exception is thrown when weight mismatch occurs.
     */
    @Test
    public void testWrongWeight() {
        StubBarcodeScanner stubBarcodeScanner = new StubBarcodeScanner();
        Assert.assertThrows(AddItemByBarcode.WeightDiscrepancyException.class, () -> addItemByBarcode.aBarcodeHasBeenScanned(stubBarcodeScanner, barcode));
    }

    /**
     * Tests the functionality of adding multiple barcoded products to the order. Verifies if the product has been successfully added to the order list and if the barcode scanner has been disabled.
     */
    @Test
    public void testAddBarcodedProductsToOrder() {
        StubBarcodeScanner stubBarcodeScanner = new StubBarcodeScanner();
        electronicScale.addAnItem(item); // use stub to simulate weight change
        addItemByBarcode.aBarcodeHasBeenScanned(stubBarcodeScanner, barcode);
        assertEquals(1, addItemByBarcode.getOrder().size());
        electronicScale.addAnItem(item); // use stub to simulate weight change
        addItemByBarcode.aBarcodeHasBeenScanned(stubBarcodeScanner, barcode);
        assertEquals(2, addItemByBarcode.getOrder().size());
    }

    /**
     * Stub class for simulating barcode scanning. This class simulates the behavior of a barcode scanner, allowing for testing of the AddItemByBarcode class.
     */
    static class StubBarcodeScanner implements IBarcodeScanner {

        /**
         * Indicates whether the barcode scanner is disabled.
         */
        private boolean disabled = false;
        /**
         * Indicates whether a barcode has been scanned.
         */
        private boolean barcodeScanned = false;

        @Override
        public void enable() {
            disabled = false;
        }

        @Override
        public void disable() {
            disabled = true;
        }

        public boolean isDisabled() {
            return disabled;
        }

        @Override
        public List<BarcodeScannerListener> listeners( ) {
            return null;
        }

        @Override
        public boolean isPluggedIn( ) {
            return false;
        }

        public boolean isPoweredUp() {
            return true;
        }

        @Override
        public void plugIn(PowerGrid grid) {
        }

        @Override
        public void unplug( ) {
        }

        @Override
        public void turnOn( ) {
        }

        @Override
        public void turnOff( ) {
        }

        @Override
        public boolean deregister(BarcodeScannerListener listener) {
            return false;
        }

        @Override
        public void deregisterAll( ) {
        }

        @Override
        public void register(BarcodeScannerListener listener) {
        }

        /**
         * Notifies the barcode scanner that a barcode has been scanned.
         */
        protected void notifyBarcodeScanned() {
            barcodeScanned = true;
        }
        @Override
        public void scan(BarcodedItem item) {
            notifyBarcodeScanned();
        }
    }

}
