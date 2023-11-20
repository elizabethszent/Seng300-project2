package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.external.*;
import java.math.BigDecimal;


/**
 * Class to handle payments made by swiping a credit card.
 *
 */
public class PayViaCreditSwipe {
    CardIssuer issuer;
    public PayViaCreditSwipe(CardIssuer issuer) {
        this.issuer = issuer;
    }

    /**
     * Method to handle a credit card being swiped.
     * Communicates details of payment and card information to the bank.
     *	@return true if the credit card payment was successful, false otherwise
     */
    public boolean creditCardScanned(String cardNumber, BigDecimal amountDue) {
        long holdNumber = issuer.authorizeHold(cardNumber, amountDue.doubleValue());
        boolean success = issuer.postTransaction(cardNumber, holdNumber, amountDue.doubleValue());
        issuer.releaseHold(cardNumber, holdNumber);
        return success;
    }
}