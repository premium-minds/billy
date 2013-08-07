package com.premiumminds.billy.portugal.services.export.pdf.simpleinvoice;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractExportRequest;

public class PDFPTSimpleInvoiceExportRequest extends AbstractExportRequest {

	private PTSimpleInvoiceTemplateBundle bundle;

	public PDFPTSimpleInvoiceExportRequest(UID uid,
			PTSimpleInvoiceTemplateBundle bundle) {
		super(uid, bundle);
	}

	@Override
	public PTSimpleInvoiceTemplateBundle getBundle() {
		return this.bundle;
	}
}
