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
package com.premiumminds.billy.portugal.services.export.pdf.invoice;

import com.premiumminds.billy.core.services.UID;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractExportRequest;

public class PTInvoicePDFExportRequest extends AbstractExportRequest {

    public PTInvoicePDFExportRequest(UID uid, PTInvoiceTemplateBundle bundle) {
        super(uid, bundle);
    }

    public PTInvoicePDFExportRequest(UID uid, PTInvoiceTemplateBundle bundle, String resultPath) {
        super(uid, bundle, resultPath);
    }

    @Override
    public PTInvoiceTemplateBundle getBundle() {
        return (PTInvoiceTemplateBundle) this.bundle;
    }
}
