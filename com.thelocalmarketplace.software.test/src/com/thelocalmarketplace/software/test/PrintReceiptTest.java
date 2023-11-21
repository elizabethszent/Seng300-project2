package com.thelocalmarketplace.software.test;

import com.thelocalmarketplace.hardware.Product; // Use the external Product class
import com.thelocalmarketplace.software.PrintReceipt;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * This class contains JUnit test cases to verify the functionality of the PrintReceipt class.
 */
public class PrintReceiptTest {

    /**
     * Test case to verify the receipt formatting with an empty list of products.
     */
    @Test
    public void testReceiptFormattingWithEmptyProductList() {
        PrintReceipt printReceipt = new PrintReceipt(null);
        List<Product> products = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        List<String> formattedReceipt = printReceipt.formatReceiptContent(products, totalAmount);
        assertTrue("Receipt should indicate no items purchased", formattedReceipt.stream().anyMatch(s -> s.contains("Items Purchased:")));
        assertTrue("Receipt should show a total amount of $0.00", formattedReceipt.stream().anyMatch(s -> s.contains("Total: $0.00")));
    }

    /**
     * Test case to verify receipt formatting with null product list.
     */
    @Test(expected = NullPointerException.class)
    public void testReceiptFormattingWithNullProductList() {
        PrintReceipt printReceipt = new PrintReceipt(null);
        printReceipt.formatReceiptContent(null, BigDecimal.ZERO);
    }


}
