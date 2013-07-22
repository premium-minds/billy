package com.premiumminds.billy.portugal.persistence.entities;

import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;

public interface PTInvoiceEntity extends GenericInvoiceEntity, PTInvoice {

	public void setCancelled(boolean cancelled);

	public void setBilled(boolean billed);

	public void setHash(String hash);

	public void setSourceHash(String source);
}
