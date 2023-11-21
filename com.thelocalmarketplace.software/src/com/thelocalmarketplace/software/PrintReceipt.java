package com.thelocalmarketplace.software;

import com.jjjwelectronics.printer.IReceiptPrinter;
import com.thelocalmarketplace.hardware.Product;
import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.OverloadedDevice;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Class responsible for handling the printing of receipts for customer transactions.
 * It interacts with an IReceiptPrinter to physically print the receipt.
 */
public class PrintReceipt {

    private IReceiptPrinter printer;

    /**
     * Constructs a PrintReceipt object with the specified receipt printer.
     *
     * @param printer The printer interface used for printing the receipt.
     */
    public PrintReceipt(IReceiptPrinter printer) {
        this.printer = printer;
    }

    /**
     * Executes the printing process for a given transaction.
     *
     * @param purchasedItems The list of purchased items.
     * @param totalAmount    The total amount of the transaction.
     */
    public void executePrint(List<Product> purchasedItems, BigDecimal totalAmount) {
        try {
            printReceipt(purchasedItems, totalAmount);
        } catch (IOException | EmptyDevice | OverloadedDevice e) {
            handlePrinterException(e);
        }
    }

    /**
     * Handles the process of formatting and printing the receipt.
     *
     * @param purchasedItems The list of items purchased.
     * @param totalAmount    The total amount of the transaction.
     * @throws IOException        If an IO error occurs during printing.
     * @throws EmptyDevice       If the printer runs out of ink or paper.
     * @throws OverloadedDevice  If too many characters are sent to a line of the printer.
     */
    private void printReceipt(List<Product> purchasedItems, BigDecimal totalAmount) throws IOException, EmptyDevice, OverloadedDevice {
        List<String> formattedContent = formatReceiptContent(purchasedItems, totalAmount);
        for (String line : formattedContent) {
            for (char c : line.toCharArray()) {
                printer.print(c);
            }
            printer.print('\n');
        }
        printer.cutPaper();
    }

    /**
     * Formats the content of the receipt.
     *
     * @param purchasedItems The list of items purchased.
     * @param totalAmount    The total amount of the transaction.
     * @return A list of strings representing the formatted receipt content.
     */
    private List<String> formatReceiptContent(List<Product> purchasedItems, BigDecimal totalAmount) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = LocalDateTime.now().format(formatter);

        List<String> itemsFormatted = purchasedItems.stream()
                .map(item -> String.format("%s - $%.2f", item.getName(), item.getPrice()))
                .collect(Collectors.toList());

        String totalFormatted = String.format("Total: $%.2f", totalAmount);

        return List.of(
                "The Local Marketplace",
                "Date: " + dateTime,
                "Items Purchased:",
                String.join("\n", itemsFormatted),
                totalFormatted,
                "Thank you for shopping with us!"
        );
    }

    /**
     * Handles exceptions that may occur during the printing process.
     *
     * @param e The exception encountered during printing.
     */
    private void handlePrinterException(Exception e) {
        System.err.println("Printer error: " + e.getMessage());
    }

}
