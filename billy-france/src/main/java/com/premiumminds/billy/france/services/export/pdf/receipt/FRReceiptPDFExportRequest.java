/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.services.export.pdf.receipt;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.france.services.export.pdf.invoice.FRInvoiceTemplateBundle;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractExportRequest;

public class FRReceiptPDFExportRequest extends AbstractExportRequest {

    public FRReceiptPDFExportRequest(StringID<GenericInvoice> uid, FRReceiptTemplateBundle bundle) {
        super(uid, bundle);
    }

    public FRReceiptPDFExportRequest(StringID<GenericInvoice> uid, FRInvoiceTemplateBundle bundle, String resultPath) {
        super(uid, bundle, resultPath);
    }

    @Override
    public FRReceiptTemplateBundle getBundle() {
        return (FRReceiptTemplateBundle) this.bundle;
    }

}
