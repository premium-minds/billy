package com.premiumminds.billy.portugal.test.fixtures;

import com.premiumminds.billy.core.test.fixtures.MockGenericInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;

public class MockPTInvoiceEntity extends MockGenericInvoiceEntity implements
		PTInvoiceEntity {

	private static final long serialVersionUID = 1L;

	protected Boolean cancelled;
	protected Boolean billed;

	public MockPTInvoiceEntity() {

	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public boolean isBilled() {
		return billed;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public void setBilled(boolean billed) {
		this.billed = billed;
	}

}
