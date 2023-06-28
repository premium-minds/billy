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
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.AddressData;
import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.ContactData;
import com.premiumminds.billy.gin.services.export.CostumerData;
import com.premiumminds.billy.gin.services.export.GenericInvoiceData;
import com.premiumminds.billy.gin.services.export.InvoiceEntryData;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ProductData;
import com.premiumminds.billy.gin.services.export.TaxExemption;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;

class AbstractFOPPDFTransformerTest {

    static class DummyFOPPDFTransformer extends AbstractFOPPDFTransformer{

        public DummyFOPPDFTransformer(
            final Class transformableClass,
            final MathContext mc,
            final String logoImagePath,
            final InputStream xsltFileStream)
        {
            super(transformableClass, mc, logoImagePath, xsltFileStream);
        }

        @Override
        public void transform(final GenericInvoiceData entity, final Object output) throws ExportServiceException {

        }

        @Override
        protected String getPaymentMechanismTranslation(final Enum pmc) {
            return null;
        }

        @Override
        protected String getCustomerFinancialId(final GenericInvoiceData document) {
            return null;
        }

        @Override
        protected ParamsTree<String, String> getNewParamsTree() {
            return new ParamsTree<>("invoice");
        }
    }

    static class DummyFOPPDFTransformerQRCode extends DummyFOPPDFTransformer{

        public DummyFOPPDFTransformerQRCode(
            final Class transformableClass,
            final MathContext mc,
            final String logoImagePath,
            final InputStream xsltFileStream)
        {
            super(transformableClass, mc, logoImagePath, xsltFileStream);
        }

        @Override
        protected ParamsTree<String, String> getNewParamsTree() {
            final ParamsTree<String, String> params = super.getNewParamsTree();
            params.getRoot().addChild("qrCode", "A:123456789*B:999999990*C:PT*D:FT*E:N*F:20191231*G:FTAB2019/0035*H:CSDF7T5H-0035*I1:PT*I2:12000.00*I3:15000.00*I4:900.00*I5:50000.00*I6:6500.00*I7:80000.00*I8:18400.00*J1:PT-AC*J2:10000.00*J3:25000.56*J4:1000.02*J5:75000.00*J6:6750.00*J7:100000.00*J8:18000.00*K1:PT-MA*K2:5000.00*K3:12500.00*K4:625.00*K5:25000.00*K6:3000.00*K7:40000.00*K8:8800.00*L:100.00*M:25.00*N:64000.02*O:513600.58*P:100.00*Q:kLp0*R:9999*S:TB;PT00000000000000000000000;513500.58");
            return params;
        }
    }

    @Test
    void testTransform() throws Exception {

        final CostumerData customer = new CostumerData(
            StringID.fromValue("uid1"),
            "taxRegristrationNumber1",
            "name1",
            new AddressData("country1",
                            "details1",
                            "city1",
                            "region1",
                            "postalCode1"));
        final BusinessData business = new BusinessData(
            "name2",
            "financialId2",
            new AddressData("country2",
                            "details2",
                            "city2",
                            "region2",
                            "postalCode2"),
            new ContactData("phone2", "fax2", "email2"));
        GenericInvoiceData genericInvoiceData = new GenericInvoiceData(
            "number1",
            LocalDate.now(),
            new Date(),
            new ArrayList<>(), customer, business, List.of(new InvoiceEntryData(
            new ProductData("productCode1", "description1"),
            "description2",
            BigDecimal.ONE,
            BigDecimal.ONE,
            BigDecimal.ONE,
            BigDecimal.ONE,
            BigDecimal.ONE,
            new ArrayList<>(),
            "unitOfMeasure1",
            TaxExemption.setExemption("exemptionCode1", "exemptionReason1"))),
            BigDecimal.ONE,
            BigDecimal.ONE,
            BigDecimal.ONE,
            "settlementDescription1");


        try (InputStream xsl = getClass().getResourceAsStream("/templates/invoice.xsl")) {

            final DummyFOPPDFTransformer dummyFOPPDFTransformer = new DummyFOPPDFTransformer(
                null,
                BillyMathContext.get(),
                null,
                xsl);
            dummyFOPPDFTransformer.transform(genericInvoiceData, new ByteArrayOutputStream());
        }
    }

    @Test
    void testTransformWithBigQrCode() throws Exception {

        final CostumerData customer = new CostumerData(
            StringID.fromValue("uid1"),
            "taxRegristrationNumber1",
            "name1",
            new AddressData("country1",
                            "details1",
                            "city1",
                            "region1",
                            "postalCode1"));
        final BusinessData business = new BusinessData(
            "name2",
            "financialId2",
            new AddressData("country2",
                            "details2",
                            "city2",
                            "region2",
                            "postalCode2"),
            new ContactData("phone2", "fax2", "email2"));
        GenericInvoiceData genericInvoiceData = new GenericInvoiceData(
            "number1",
            LocalDate.now(),
            new Date(),
            new ArrayList<>(), customer, business, List.of(new InvoiceEntryData(
            new ProductData("productCode1", "description1"),
            "description2",
            BigDecimal.ONE,
            BigDecimal.ONE,
            BigDecimal.ONE,
            BigDecimal.ONE,
            BigDecimal.ONE,
            new ArrayList<>(),
            "unitOfMeasure1",
            TaxExemption.setExemption("exemptionCode1", "exemptionReason1"))),
            BigDecimal.ONE,
            BigDecimal.ONE,
            BigDecimal.ONE,
            "settlementDescription1");


        try (InputStream xsl = getClass().getResourceAsStream("/templates/invoice.xsl")) {

            final DummyFOPPDFTransformer dummyFOPPDFTransformer = new DummyFOPPDFTransformerQRCode(
                null,
                BillyMathContext.get(),
                null,
                xsl);
            dummyFOPPDFTransformer.transform(genericInvoiceData, new ByteArrayOutputStream());
        }
    }

}
