package com.premiumminds.billy.portugal.services.entities;

import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;

public interface PTInvoice extends GenericInvoice {

	public boolean isCancelled();

	public boolean isBilled();

	public String getHash();

	public String getSourceHash();
}
