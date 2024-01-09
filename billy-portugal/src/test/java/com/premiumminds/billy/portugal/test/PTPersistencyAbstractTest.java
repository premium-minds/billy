/*
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
package com.premiumminds.billy.portugal.test;

import com.google.inject.Guice;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.persistence.entities.jpa.JPAInvoiceSeriesEntity;
import com.premiumminds.billy.portugal.PortugalBootstrap;
import com.premiumminds.billy.portugal.PortugalDependencyModule;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParamsImpl;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.SourceBilling;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;
import com.premiumminds.billy.portugal.test.util.PTBusinessTestUtil;
import com.premiumminds.billy.portugal.test.util.PTCreditNoteTestUtil;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;
import com.premiumminds.billy.portugal.test.util.PTReceiptInvoiceTestUtil;
import com.premiumminds.billy.portugal.util.KeyGenerator;
import com.premiumminds.billy.portugal.util.Services;

public class PTPersistencyAbstractTest extends PTAbstractTest {

    protected static final String PRIVATE_KEY_DIR = "/keys/private.pem";
    protected static final String DEFAULT_SERIES = "DEFAULT";

    @BeforeEach public void setUpModules() {
        PTAbstractTest.injector = Guice.createInjector(new PortugalDependencyModule(),
            new PortugalTestPersistenceDependencyModule());
        PTAbstractTest.injector.getInstance(PortugalDependencyModule.Initializer.class);
        PTAbstractTest.injector.getInstance(PortugalTestPersistenceDependencyModule.Initializer.class);
        PortugalBootstrap.execute(PTAbstractTest.injector);
    }

    @AfterEach public void tearDown() {
        PTAbstractTest.injector.getInstance(PortugalTestPersistenceDependencyModule.Finalizer.class);
    }

    public PTInvoiceEntity getNewIssuedInvoice() {
        return this.getNewIssuedInvoice(StringID.fromValue(UUID.randomUUID().toString()));

    }

    public PTInvoiceEntity getNewIssuedInvoice(StringID<Business> businessUID) {
        Services service = new Services(PTAbstractTest.injector);
        PTIssuingParams parameters = this.getParameters(PTPersistencyAbstractTest.DEFAULT_SERIES, "30000", "1");

        try {
            return (PTInvoiceEntity) service.issueDocument(
                new PTInvoiceTestUtil(PTAbstractTest.injector).getInvoiceBuilder(
                    new PTBusinessTestUtil(PTAbstractTest.injector).getBusinessEntity(businessUID), SourceBilling.P),
                parameters);
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    public PTCreditNoteEntity getNewIssuedCreditnote(PTInvoice reference) {
        Services service = new Services(PTAbstractTest.injector);
        PTIssuingParams parameters = this.getParameters("NC", "30000", "1");

        this.createSeries(reference, "NC", Optional.of("CCCC2345"));

        try {
            return (PTCreditNoteEntity) service.issueDocument(
                new PTCreditNoteTestUtil(PTAbstractTest.injector).getCreditNoteBuilder((PTInvoiceEntity) reference),
                parameters);
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    protected PTIssuingParams getParameters(String series, String EACCode, String privateKeyVersion) {
        PTIssuingParams parameters = new PTIssuingParamsImpl();
        KeyGenerator generator = new KeyGenerator(getClass().getResource(PRIVATE_KEY_DIR));
        parameters.setPrivateKey(generator.getPrivateKey());
        parameters.setPublicKey(generator.getPublicKey());
        parameters.setPrivateKeyVersion(privateKeyVersion);
        parameters.setEACCode(EACCode);
        parameters.setInvoiceSeries(series);
        return parameters;
    }

    protected void createSeries(StringID<Business> businessUID) {
        this.createSeries(new PTReceiptInvoiceTestUtil(PTAbstractTest.injector).getReceiptInvoiceBuilder(
                new PTBusinessTestUtil(PTAbstractTest.injector).getBusinessEntity(businessUID), SourceBilling.P).build(),
            PTPersistencyAbstractTest.DEFAULT_SERIES, Optional.of("CCCC2345"));
    }

    protected void createSeries(StringID<Business> businessUID, String series) {
        this.createSeries(new PTReceiptInvoiceTestUtil(PTAbstractTest.injector).getReceiptInvoiceBuilder(
                new PTBusinessTestUtil(PTAbstractTest.injector).getBusinessEntity(businessUID), SourceBilling.P).build(),
            series, Optional.of("CCCC2345"));
    }

    protected void createSeries(StringID<Business> businessUID, String series, Optional<String> code) {
        this.createSeries(new PTReceiptInvoiceTestUtil(PTAbstractTest.injector).getReceiptInvoiceBuilder(
                new PTBusinessTestUtil(PTAbstractTest.injector).getBusinessEntity(businessUID), SourceBilling.P).build(),
            series, code);
    }

    protected <T extends GenericInvoice> void createSeries(T document, String series) {
        this.createSeries(document, series, Optional.of("CCCC2345"));
    }

    protected <T extends GenericInvoice> void createSeries(T document, String series, Optional<String> code) {
        final DAOInvoiceSeries daoInvoiceSeries = PTAbstractTest.injector.getInstance(DAOInvoiceSeries.class);
        final JPAInvoiceSeriesEntity seriesEntity = new JPAInvoiceSeriesEntity();
        seriesEntity.setSeries(series);
        seriesEntity.setBusiness(document.getBusiness());
        code.ifPresent(seriesEntity::setSeriesUniqueCode);
        daoInvoiceSeries.create(seriesEntity);
    }
}
