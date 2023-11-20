package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.*;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.card.*;
import com.jjjwelectronics.card.Card.CardData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Class to handle payments made by swiping a credit card.
 *
 */
public class PayViaCreditSwipe implements CardReaderListener {
    CardIssuer issuer;
    ArrayList<String> creditTypes = new ArrayList<String>(Arrays.asList("Visa", "Mastercard"));
    BigDecimal amountDue;
    // TODO: when main is created, add it as a field here so the 
    // amount due can be modified when payment occurs

    // Ex: 
    // MainSoftware control;
 
    // Once payment is made, we can do something like:
    // control.setAmountDue(new BigDecimal(0));
    
    public PayViaCreditSwipe(CardIssuer issuer, AbstractSelfCheckoutStation station, 
    		BigDecimal amountDue, Session session
    		// MainSoftware control
    		) {
    	super();
        this.issuer = issuer;
        this.amountDue = amountDue;
        // this.control = control;
    }

    /**
     * Method to handle credit card data being read.
     * Communicates details of payment and card information to the bank.
     * You can tell when if the payment wasn't successful if the amount due doesn't change.
     */
    public void theDataFromACardHasBeenRead(CardData data) {
    	if (creditTypes.contains(data.getType())) {
    		String cardNumber = data.getNumber();
    		
    		long holdNumber = issuer.authorizeHold(cardNumber, amountDue.doubleValue());
            boolean success = issuer.postTransaction(cardNumber, holdNumber, amountDue.doubleValue());
            
            // TODO: when main is created, change those last two lines to:
            // long holdNumber = issuer.authorizeHold(cardNumber, control.getAmountDue());
            // boolean success = issuer.postTransaction(cardNumber, holdNumber, control.getAmountDue());
            
            issuer.releaseHold(cardNumber, holdNumber);
            
            if (success) {
            	// TODO: when main is created, add the line:
            	// control.setAmountDue(new BigDecimal(0));
            	
                System.out.println("New amount due: 0");
                
            	// TODO: when print receipt is implemented do that here:
            	// control.receiptPrinter.printReceipt();
            }
    	}
    }
    
    @Override
	public void aCardHasBeenSwiped() {
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
	}

}