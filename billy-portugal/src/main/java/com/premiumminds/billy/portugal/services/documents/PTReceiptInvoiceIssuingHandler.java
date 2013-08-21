package com.premiumminds.billy.portugal.services.documents;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTReceiptInvoice;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;


public class PTReceiptInvoiceIssuingHandler extends
		PTGenericInvoiceIssuingHandler implements DocumentIssuingHandler {
	
	public final static TYPE INVOICE_TYPE = TYPE.FR;

	public PTReceiptInvoiceIssuingHandler(Injector injector) {
		super(injector);
	}

	@Override
	public <T extends GenericInvoice, P extends IssuingParams> T issue(
			T document, P parameters) throws DocumentIssuingException {
		
		final PTIssuingParams parametersPT = (PTIssuingParams) parameters;
		
		final DAOPTReceiptInvoice daoReceiptInvoice = this.injector.getInstance(DAOPTReceiptInvoice.class);
		
		return this.issue(document, parametersPT, daoReceiptInvoice, PTReceiptInvoiceIssuingHandler.INVOICE_TYPE);
	}

}
