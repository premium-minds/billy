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
package com.premiumminds.billy.france.test;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.google.inject.Guice;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import com.premiumminds.billy.france.FranceBootstrap;
import com.premiumminds.billy.france.FranceDependencyModule;
import com.premiumminds.billy.france.persistence.entities.FRCreditNoteEntity;
import com.premiumminds.billy.france.persistence.entities.FRCreditReceiptEntity;
import com.premiumminds.billy.france.persistence.entities.FRInvoiceEntity;
import com.premiumminds.billy.france.persistence.entities.FRReceiptEntity;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParams;
import com.premiumminds.billy.france.services.documents.util.FRIssuingParamsImpl;
import com.premiumminds.billy.france.services.entities.FRInvoice;
import com.premiumminds.billy.france.services.entities.FRReceipt;
import com.premiumminds.billy.france.test.util.FRBusinessTestUtil;
import com.premiumminds.billy.france.test.util.FRCreditNoteTestUtil;
import com.premiumminds.billy.france.test.util.FRCreditReceiptTestUtil;
import com.premiumminds.billy.france.test.util.FRInvoiceTestUtil;
import com.premiumminds.billy.france.test.util.FRReceiptTestUtil;
import com.premiumminds.billy.france.util.Services;
import com.premiumminds.billy.persistence.entities.jpa.JPAInvoiceSeriesEntity;

public class FRPersistencyAbstractTest extends FRAbstractTest {

    protected static final String PRIVATE_KEY_DIR = "/keys/private.pem";
    protected static final String DEFAULT_SERIES = "DEFAULT";

    @BeforeEach
    public void setUpModules() {
        FRAbstractTest.injector =
                Guice.createInjector(new FranceDependencyModule(), new FranceTestPersistenceDependencyModule());
        FRAbstractTest.injector.getInstance(FranceDependencyModule.Initializer.class);
        FRAbstractTest.injector.getInstance(FranceTestPersistenceDependencyModule.Initializer.class);
        FranceBootstrap.execute(FRAbstractTest.injector);
    }

    @AfterEach
    public void tearDown() {
        FRAbstractTest.injector.getInstance(FranceTestPersistenceDependencyModule.Finalizer.class);
    }

    public FRInvoiceEntity getNewIssuedInvoice() {
        return this.getNewIssuedInvoice(StringID.fromValue(UUID.randomUUID().toString()));

    }

    public FRInvoiceEntity getNewIssuedInvoice(StringID<Business> businessUID) {
        Services service = new Services(FRAbstractTest.injector);
        FRIssuingParams parameters = new FRIssuingParamsImpl();

        parameters = this.getParameters(FRPersistencyAbstractTest.DEFAULT_SERIES, "30000");

        try {
            return (FRInvoiceEntity) service.issueDocument(
                    new FRInvoiceTestUtil(FRAbstractTest.injector).getInvoiceBuilder(
                            new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(businessUID)),
                    parameters);
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    public FRReceiptEntity getNewIssuedReceipt(StringID<Business> businessUID) {
        Services service = new Services(FRAbstractTest.injector);
        FRIssuingParams parameters = new FRIssuingParamsImpl();

        parameters = this.getParameters(FRPersistencyAbstractTest.DEFAULT_SERIES, "007");

        try {
            return (FRReceiptEntity) service.issueDocument(
                    new FRReceiptTestUtil(FRAbstractTest.injector).getReceiptBuilder(
                            new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(businessUID)),
                    parameters);
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    public FRCreditReceiptEntity getNewIssuedCreditReceipt(FRReceipt receipt) {
        Services service = new Services(FRAbstractTest.injector);
        FRIssuingParams parameters = new FRIssuingParamsImpl();

        parameters = this.getParameters("RC", "30000");

        this.createSeries(receipt, "RC");

        try {
            return (FRCreditReceiptEntity) service.issueDocument(
                    new FRCreditReceiptTestUtil(FRAbstractTest.injector).getCreditReceiptBuilder((FRReceiptEntity) receipt),
                    parameters);
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    public FRCreditNoteEntity getNewIssuedCreditnote(FRInvoice reference) {
        Services service = new Services(FRAbstractTest.injector);
        FRIssuingParams parameters = new FRIssuingParamsImpl();

        parameters = this.getParameters("NC", "30000");
        this.createSeries(reference, "NC");

        try {
            return (FRCreditNoteEntity) service.issueDocument(
                    new FRCreditNoteTestUtil(FRAbstractTest.injector).getCreditNoteBuilder((FRInvoiceEntity) reference),
                    parameters);
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    protected FRIssuingParams getParameters(String series, String EACCode) {
        FRIssuingParams parameters = new FRIssuingParamsImpl();
        parameters.setEACCode(EACCode);
        parameters.setInvoiceSeries(series);
        return parameters;
    }

    protected void createSeries(StringID<Business> businessUID) {
        this.createSeries(
            new FRReceiptTestUtil(FRAbstractTest.injector).getReceiptBuilder(
                new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(businessUID)).build(),
            FRPersistencyAbstractTest.DEFAULT_SERIES);
    }

    protected void createSeries(StringID<Business> businessUID, String series) {
        this.createSeries(
            new FRReceiptTestUtil(FRAbstractTest.injector).getReceiptBuilder(
                new FRBusinessTestUtil(FRAbstractTest.injector).getBusinessEntity(businessUID)).build(),
            series);
    }

    protected <T extends GenericInvoice> void createSeries(T document, String series) {
        final DAOInvoiceSeries daoInvoiceSeries = FRAbstractTest.injector.getInstance(DAOInvoiceSeries.class);
        final JPAInvoiceSeriesEntity seriesEntity = new JPAInvoiceSeriesEntity();
        seriesEntity.setSeries(series);
        seriesEntity.setBusiness(document.getBusiness());
        daoInvoiceSeries.create(seriesEntity);
    }
}
