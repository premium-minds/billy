package com.premiumminds.billy.portugal.services.documents;

import com.premiumminds.billy.core.services.documents.DocumentIssuingHandler;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.services.certification.CertificationManager;

public class PTInvoiceIssuingHandler implements DocumentIssuingHandler {

	@Override
	public <T extends GenericInvoice> T issue(T document)
			throws DocumentIssuingException {
		CertificationManager m = new CertificationManager();
		return document;
	}

}
