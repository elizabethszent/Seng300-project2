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
    AbstractSelfCheckoutStation station;
    ArrayList<String> creditTypes = new ArrayList<String>(Arrays.asList("Visa", "Mastercard"));
    
    public PayViaCreditSwipe(CardIssuer issuer, AbstractSelfCheckoutStation station) {
        this.issuer = issuer;
        this.station = station;
    }

    /**
     * Method to handle a credit card being swiped.
     * Communicates details of payment and card information to the bank.
     *	@return true if the credit card payment was successful, false otherwise
     */
    public void theDataFromACardHasBeenRead(CardData data) {
    	if (creditTypes.contains(data.getType())) {
    		String cardNumber = data.getNumber();
    		
    		long holdNumber = issuer.authorizeHold(cardNumber, amountDue.doubleValue());
            boolean success = issuer.postTransaction(cardNumber, holdNumber, amountDue.doubleValue());
            issuer.releaseHold(cardNumber, holdNumber);
            if (success)
                System.out.println("New amount due: 0");
    	}
    }

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aCardHasBeenSwiped() {
		// TODO Auto-generated method stub
		
	}
}