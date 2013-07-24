package com.premiumminds.billy.portugal.services.documents;

import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;

public abstract class AbstractPTGenericInvoiceIssuingHandler implements
		DocumentIssuingHandler {

	@Override
	public abstract <T extends GenericInvoice> T issue(T document)
			throws DocumentIssuingException;
}
