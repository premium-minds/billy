package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;

public class Services {

	private final Injector injector;
	private DocumentIssuingService issuingService;
	
	
	public Services(Injector injector) {
		this.injector = injector;
		issuingService = injector.getInstance(DocumentIssuingService.class);
		setupServices();
	}

	private void setupServices() {
		issuingService.addHandler(PTInvoice.class, injector.getInstance(PTInvoiceIssuingHandler.class));
	}
	
	public <T extends PTInvoice> T issueDocument(Builder<T> b) throws DocumentIssuingException {
		return issuingService.issue(b);
	}
	
}
