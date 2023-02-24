/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy GIN.
 *
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services.impl.pdf;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.gin.services.ExportServiceRequest;
import com.premiumminds.billy.gin.services.export.BillyTemplateBundle;

public class AbstractExportRequest implements ExportServiceRequest {

    private static final String DEFAULT_PATH_TEMPLATE =
            System.getProperty("java.io.tmpdir") + "/billy_export_request_%s_%d.pdf";

    protected StringID<GenericInvoice> uid;
    protected BillyTemplateBundle bundle;
    protected String resultPath;

    public AbstractExportRequest(StringID<GenericInvoice> uid, BillyTemplateBundle bundle) {
        this.uid = uid;
        this.bundle = bundle;
        this.resultPath =
                String.format(AbstractExportRequest.DEFAULT_PATH_TEMPLATE, uid.toString(), System.currentTimeMillis());
    }

    public AbstractExportRequest(StringID<GenericInvoice> uid, BillyTemplateBundle bundle, String resultPath) {
        this.uid = uid;
        this.bundle = bundle;
        this.resultPath = resultPath;
    }

    public StringID<GenericInvoice> getDocumentUID() {
        return this.uid;
    }

    @Override
    public BillyTemplateBundle getBundle() {
        return this.bundle;
    }

    @Override
    public String getResultPath() {
        return this.resultPath;
    }

}
