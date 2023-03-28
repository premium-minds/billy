/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy andorra (AD Pack).
 *
 * billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.andorra.services.export.pdf.creditnote;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.gin.services.impl.pdf.AbstractExportRequest;

public class ADCreditNotePDFExportRequest extends AbstractExportRequest {

    public ADCreditNotePDFExportRequest(StringID<GenericInvoice> uid, ADCreditNoteTemplateBundle bundle) {
        super(uid, bundle);
    }

    public ADCreditNotePDFExportRequest(StringID<GenericInvoice> uid, ADCreditNoteTemplateBundle bundle, String resultPath) {
        super(uid, bundle, resultPath);
    }

    @Override
    public ADCreditNoteTemplateBundle getBundle() {
        return (ADCreditNoteTemplateBundle) this.bundle;
    }
}
