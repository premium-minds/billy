package com.premiumminds.billy.portugal.test.fixtures;

import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntryEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;

public class MockPTCreditNoteEntryEntity extends MockGenericInvoiceEntryEntity implements PTCreditNoteEntryEntity{
	
	private static final long serialVersionUID = 1L;
	private PTInvoice reference;
	private String reason;
	
	public MockPTCreditNoteEntryEntity(){
		
	}
	
	@Override
	public PTInvoice getReference(){
		return reference;
	}
	
	@Override
	public String getReason(){
		return reason;
	}
	
	@Override
	public void setReference(PTInvoice reference){
		this.reference = reference;
	}
	
	@Override
	public void setReason(String reason){
		this.reason = reason;
	}

}
