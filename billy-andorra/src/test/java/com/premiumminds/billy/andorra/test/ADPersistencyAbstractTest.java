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
package com.premiumminds.billy.andorra.test;

import com.premiumminds.billy.andorra.AndorraBootstrap;
import com.premiumminds.billy.andorra.AndorraDependencyModule;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditNoteEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADCreditReceiptEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADInvoiceEntity;
import com.premiumminds.billy.andorra.persistence.entities.ADReceiptEntity;
import com.premiumminds.billy.andorra.test.util.ADBusinessTestUtil;
import com.premiumminds.billy.andorra.test.util.ADCreditNoteTestUtil;
import com.premiumminds.billy.andorra.test.util.ADCreditReceiptTestUtil;
import com.premiumminds.billy.andorra.test.util.ADInvoiceTestUtil;
import com.premiumminds.billy.andorra.test.util.ADReceiptTestUtil;
import com.premiumminds.billy.andorra.util.Services;
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
import com.premiumminds.billy.persistence.entities.jpa.JPAInvoiceSeriesEntity;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParamsImpl;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.andorra.services.entities.ADReceipt;

public class ADPersistencyAbstractTest extends ADAbstractTest {

    protected static final String PRIVATE_KEY_DIR = "/keys/private.pem";
    protected static final String DEFAULT_SERIES = "DEFAULT";

    @BeforeEach
    public void setUpModules() {
        ADAbstractTest.injector =
                Guice.createInjector(new AndorraDependencyModule(), new AndorraTestPersistenceDependencyModule());
        ADAbstractTest.injector.getInstance(AndorraDependencyModule.Initializer.class);
        ADAbstractTest.injector.getInstance(AndorraTestPersistenceDependencyModule.Initializer.class);
        AndorraBootstrap.execute(ADAbstractTest.injector);
    }

    @AfterEach
    public void tearDown() {
        ADAbstractTest.injector.getInstance(AndorraTestPersistenceDependencyModule.Finalizer.class);
    }

    public ADInvoiceEntity getNewIssuedInvoice() {
        return this.getNewIssuedInvoice(StringID.fromValue(UUID.randomUUID().toString()));

    }

    public ADInvoiceEntity getNewIssuedInvoice(StringID<Business> businessUID) {
        Services service = new Services(ADAbstractTest.injector);
        ADIssuingParams parameters = new ADIssuingParamsImpl();

        parameters = this.getParameters(ADPersistencyAbstractTest.DEFAULT_SERIES, "30000");

        try {
            return (ADInvoiceEntity) service.issueDocument(
                    new ADInvoiceTestUtil(ADAbstractTest.injector).getInvoiceBuilder(
                            new ADBusinessTestUtil(ADAbstractTest.injector).getBusinessEntity(businessUID)),
                    parameters);
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ADReceiptEntity getNewIssuedReceipt(StringID<Business> businessUID) {
        Services service = new Services(ADAbstractTest.injector);
        ADIssuingParams parameters = new ADIssuingParamsImpl();

        parameters = this.getParameters(ADPersistencyAbstractTest.DEFAULT_SERIES, "007");

        try {
            return (ADReceiptEntity) service.issueDocument(
                    new ADReceiptTestUtil(ADAbstractTest.injector).getReceiptBuilder(
                            new ADBusinessTestUtil(ADAbstractTest.injector).getBusinessEntity(businessUID)),
                    parameters);
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ADCreditReceiptEntity getNewIssuedCreditReceipt(ADReceipt receipt) {
        Services service = new Services(ADAbstractTest.injector);
        ADIssuingParams parameters = new ADIssuingParamsImpl();

        parameters = this.getParameters("RC", "30000");

        this.createSeries(receipt, "RC");

        try {
            return (ADCreditReceiptEntity) service.issueDocument(
                    new ADCreditReceiptTestUtil(ADAbstractTest.injector).getCreditReceiptBuilder((ADReceiptEntity) receipt),
                    parameters);
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ADCreditNoteEntity getNewIssuedCreditnote(ADInvoice reference) {
        Services service = new Services(ADAbstractTest.injector);
        ADIssuingParams parameters = new ADIssuingParamsImpl();

        parameters = this.getParameters("NC", "30000");

        this.createSeries(reference, "NC");

        try {
            return (ADCreditNoteEntity) service.issueDocument(
                    new ADCreditNoteTestUtil(ADAbstractTest.injector).getCreditNoteBuilder((ADInvoiceEntity) reference),
                    parameters);
        } catch (DocumentIssuingException | SeriesUniqueCodeNotFilled | DocumentSeriesDoesNotExistException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected ADIssuingParams getParameters(String series, String EACCode) {
        ADIssuingParams parameters = new ADIssuingParamsImpl();
        parameters.setEACCode(EACCode);
        parameters.setInvoiceSeries(series);
        return parameters;
    }

    protected void createSeries(StringID<Business> businessUID) {
        this.createSeries(
            new ADReceiptTestUtil(ADAbstractTest.injector).getReceiptBuilder(
                new ADBusinessTestUtil(ADAbstractTest.injector).getBusinessEntity(businessUID)).build(),
            ADPersistencyAbstractTest.DEFAULT_SERIES);
    }

    protected void createSeries(StringID<Business> businessUID, String series) {
        this.createSeries(
            new ADReceiptTestUtil(ADAbstractTest.injector).getReceiptBuilder(
                new ADBusinessTestUtil(ADAbstractTest.injector).getBusinessEntity(businessUID)).build(),
            series);
    }

    protected <T extends GenericInvoice> void createSeries(T document, String series) {
        final DAOInvoiceSeries daoInvoiceSeries = ADAbstractTest.injector.getInstance(DAOInvoiceSeries.class);
        final JPAInvoiceSeriesEntity seriesEntity = new JPAInvoiceSeriesEntity();
        seriesEntity.setSeries(series);
        seriesEntity.setBusiness(document.getBusiness());
        daoInvoiceSeries.create(seriesEntity);
    }
}
