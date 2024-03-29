/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.services.export.pdf.receipt;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractExportRequest;
import com.premiumminds.billy.spain.services.export.pdf.invoice.ESInvoiceTemplateBundle;

public class ESReceiptPDFExportRequest extends AbstractExportRequest {

    public ESReceiptPDFExportRequest(StringID<GenericInvoice> uid, ESReceiptTemplateBundle bundle) {
        super(uid, bundle);
    }

    public ESReceiptPDFExportRequest(StringID<GenericInvoice> uid, ESInvoiceTemplateBundle bundle, String resultPath) {
        super(uid, bundle, resultPath);
    }

    @Override
    public ESReceiptTemplateBundle getBundle() {
        return (ESReceiptTemplateBundle) this.bundle;
    }

}
