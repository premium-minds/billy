/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export.saftpt;

import java.io.OutputStream;
import java.util.Date;

import javax.inject.Inject;

import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.services.export.exceptions.SAFTPTExportException;

public class PTSAFTFileGenerator {

    @Inject
    private com.premiumminds.billy.portugal.services.export.saftpt.v1_02_01.PTSAFTFileGenerator saftGenV1_02_01;

    @Inject
    private com.premiumminds.billy.portugal.services.export.saftpt.v1_03_01.PTSAFTFileGenerator saftGenV1_03_01;

    public static enum SAFTVersion {
        CURRENT, V10201, V10301
    }

    /**
     * Constructs a new SAFT a.k.a. AuditFile
     *
     * @param targetStream
     *
     * @param businessEntity
     *        - the company
     * @param application
     * @param certificateNumber
     * @param fromDate
     * @param toDate
     * @param daoCustomer
     * @param daoSupplier
     * @param daoProduct
     * @param daoPTTax
     * @param daoPTRegionContext
     * @param daoPTInvoice
     * @param daoPTSimpleInvoice
     * @param daoPTCreditNote
     * @return the SAFT for that business entity, given lists of customers,
     *         products, taxes and financial documents; depends on a period of
     *         time
     * @throws SAFTPTExportException
     */
    public void generateSAFTFile(final OutputStream targetStream, final PTBusinessEntity businessEntity,
            final PTApplicationEntity application, final String certificateNumber, final Date fromDate,
            final Date toDate, final SAFTVersion version) throws SAFTPTExportException {

        switch (version) {
            case V10201:
                this.saftGenV1_02_01.generateSAFTFile(targetStream, businessEntity, application, certificateNumber,
                        fromDate, toDate);
                return;
            case V10301:
            case CURRENT:
            default:
                this.saftGenV1_03_01.generateSAFTFile(targetStream, businessEntity, application, certificateNumber,
                        fromDate, toDate);
                return;
        }

    }

}
