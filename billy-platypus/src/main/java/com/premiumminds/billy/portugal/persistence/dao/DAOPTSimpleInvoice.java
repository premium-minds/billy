package com.premiumminds.billy.portugal.persistence.dao;

import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;

public interface DAOPTSimpleInvoice extends DAOPTInvoice {

	@Override
	public PTSimpleInvoiceEntity getEntityInstance();

	@Override
	public PTSimpleInvoiceEntity getLatestInvoiceFromSeries(String series)
			throws BillyRuntimeException;
}
