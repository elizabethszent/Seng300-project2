package com.thelocalmarketplace.software.test;

import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.software.PrintReceipt;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * This class contains JUnit test cases to verify the functionality of the PrintReceipt class,
 */
public class PrintReceiptTest {

    /**
     * Test case to verify the correct formatting of the receipt with typical data.
     */
    @Test
    public void testReceiptFormattingWithTypicalData() {
        PrintReceipt printReceipt = new PrintReceipt(null);
        List<Product> products = createSampleProductList();
        BigDecimal totalAmount = BigDecimal.valueOf(3.49);

        List<String> formattedReceipt = printReceipt.formatReceiptContent(products, totalAmount);
        assertFalse("Receipt content should not be empty", formattedReceipt.isEmpty());
        assertTrue("Receipt should contain a total amount", formattedReceipt.stream().anyMatch(s -> s.contains("Total: $3.49")));
    }

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

    // Utility method to create a sample list of products
    private List<Product> createSampleProductList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Apple", BigDecimal.valueOf(0.99)));
        products.add(new Product("Bread", BigDecimal.valueOf(2.50)));
        return products;
    }

    // Mock Product class for testing purposes
    class Product {
        private String name;
        private BigDecimal price;

        public Product(String name, BigDecimal price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}
