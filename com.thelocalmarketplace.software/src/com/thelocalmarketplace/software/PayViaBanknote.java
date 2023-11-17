package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispensationSlot;
import com.tdc.banknote.BanknoteDispensationSlotObserver;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.BanknoteStorageUnitObserver;
import com.tdc.banknote.BanknoteValidator;
import com.tdc.banknote.BanknoteValidatorObserver;

/**
 * PayViaBanknote class handles payments through the use of banknotes, monitors payment process and 
 * authorizing return of change to the customer.
 * 
 * @author Jane Magai (UCID:30180119)
 * 
 */
public class PayViaBanknote implements BanknoteStorageUnitObserver,BanknoteDispensationSlotObserver,BanknoteValidatorObserver {
	private BigDecimal amount_inserted = BigDecimal.ZERO;
	private BigDecimal amount_owed;
	private BanknoteDispensationSlot banknoteDispensationSlot;
	private List <Banknote> banknotes;
		
	
	/**
	 * Constructor for PayviaCoin class.
	 * @param totalAmount 
	 */
	public PayViaBanknote() {
		this.amount_inserted = BigDecimal.ZERO;
		this.amount_owed = BigDecimal.ZERO;
		this.banknoteDispensationSlot = ;
		this.banknotes = new ArrayList<>();
		
	}
	
	public void addBanknote(Banknote banknote) {
		
		
	}
	
	
	
	/**
	 *Make a payment using banknote
	 *
	 *@param amountInserted. The banknote used for payment.
	 *@return True if payment is succesful meaning full amount is paid, false otherwise
	 * @throws CashOverloadException 
	 * @throws DisabledException 
	 */
	public boolean MakePayment (Banknote amountInserted) throws DisabledException, CashOverloadException{
		amount_inserted = amount_inserted.add(amountInserted.getDenomination());
		banknoteDispensationSlot.receive(amountInserted);
		if(amount_inserted.compareTo(amount_owed)<=0) {
			return true;
		}
		return false;
	}
	public boolean ReturnChange() {
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}	
	@Override
	public void banknotesFull(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void banknoteAdded(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void banknotesLoaded(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void banknotesUnloaded(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void banknoteDispensed(BanknoteDispensationSlot slot, List<Banknote> banknotes) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void banknotesRemoved(BanknoteDispensationSlot slot) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void goodBanknote(BanknoteValidator validator, Currency currency, BigDecimal denomination) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void badBanknote(BanknoteValidator validator) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
