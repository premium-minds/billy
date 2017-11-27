/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal Ebean (PT Pack).
 *
 * billy portugal Ebean (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal Ebean (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal Ebean (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.entities;

import java.util.List;

import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice;

public interface PTGenericInvoiceEntity extends GenericInvoiceEntity, PTGenericInvoice {

    public void setType(TYPE type);

    public void setCancelled(boolean cancelled);

    public void setBilled(boolean billed);

    public void setHash(String hash);

    public void setSourceHash(String source);

    public void setHashControl(String hashControl);

    public void setSourceBilling(SourceBilling sourceBilling);

    public void setChangeReason(String reason);

    public void setEACCode(String eacCode);

    @Override
    public <T extends GenericInvoiceEntry> List<T> getEntries();

    @Override
    public <T extends Payment> List<T> getPayments();
}
