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
package com.premiumminds.billy.andorra.its;

import com.premiumminds.billy.andorra.BillyAndorra;
import com.premiumminds.billy.andorra.AndorraBootstrap;
import com.premiumminds.billy.andorra.AndorraDependencyModule;
import com.premiumminds.billy.andorra.AndorraPersistenceDependencyModule;
import com.premiumminds.billy.core.exceptions.SeriesUniqueCodeNotFilled;
import com.premiumminds.billy.core.services.exceptions.DocumentSeriesDoesNotExistException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.persistence.dao.DAOInvoiceSeries;
import com.premiumminds.billy.core.persistence.entities.InvoiceSeriesEntity;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.core.util.PaymentMechanism;
import com.premiumminds.billy.persistence.entities.jpa.JPAInvoiceSeriesEntity;
import com.premiumminds.billy.andorra.persistence.dao.DAOADCreditNote;
import com.premiumminds.billy.andorra.persistence.dao.DAOADInvoice;
import com.premiumminds.billy.andorra.services.documents.util.ADIssuingParams;
import com.premiumminds.billy.andorra.services.entities.ADAddress;
import com.premiumminds.billy.andorra.services.entities.ADApplication;
import com.premiumminds.billy.andorra.services.entities.ADBusiness;
import com.premiumminds.billy.andorra.services.entities.ADContact;
import com.premiumminds.billy.andorra.services.entities.ADCreditNote;
import com.premiumminds.billy.andorra.services.entities.ADCreditNoteEntry;
import com.premiumminds.billy.andorra.services.entities.ADCustomer;
import com.premiumminds.billy.andorra.services.entities.ADInvoice;
import com.premiumminds.billy.andorra.services.entities.ADInvoiceEntry;
import com.premiumminds.billy.andorra.services.entities.ADPayment;
import com.premiumminds.billy.andorra.services.entities.ADProduct;
import com.premiumminds.billy.andorra.services.entities.ADTax;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvoiceIT {

    private Injector injector;

    @Test
    public void test() throws Exception {

        injector = Guice.createInjector(new AndorraDependencyModule(),
                new AndorraPersistenceDependencyModule("BillyAndorraTestPersistenceUnit"));
        injector.getInstance(AndorraDependencyModule.Initializer.class);
        injector.getInstance(AndorraPersistenceDependencyModule.Initializer.class);
        AndorraBootstrap.execute(injector);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        BillyAndorra billyAndorra = new BillyAndorra(injector);
        ADIssuingParams invoiceParameters = getAdInvoiceIssuingParams();
        ADIssuingParams creditNoteParameters = getAdCreditNoteIssuingParams();

        ADApplication.Builder applicationBuilder = getAdApplicationBuilder(billyAndorra);
        ADBusiness business = createAdBusiness(billyAndorra, applicationBuilder);
        ADCustomer customer = createAdCustomer(billyAndorra);

        createSeries(invoiceParameters.getInvoiceSeries(), business, "CCCC2345");
        createSeries(creditNoteParameters.getInvoiceSeries(), business, "CCCC2346");

        final ADTax flatTax = createFlatTax(billyAndorra);

        ADProduct product = createAdProduct(billyAndorra);
        ADProduct productExempt = createAdProductExempt(billyAndorra);
        ADProduct productFlat = createAdProductFlat(billyAndorra, flatTax);
        ADInvoice invoice = createAdInvoice(
			dateFormat,
			billyAndorra,
			business,
			invoiceParameters,
			customer,
			product,
			productExempt,
			productFlat);
        ADCreditNote creditNote = createAdCreditNote(
			dateFormat,
			billyAndorra,
			business,
			creditNoteParameters,
			customer,
			product,
			invoice);

        final DAOADInvoice daoInvoice = injector.getInstance(DAOADInvoice.class);
        final DAOADCreditNote daoCreditNote = injector.getInstance(DAOADCreditNote.class);
        assertTrue(daoInvoice.exists(invoice.getUID()));
        assertTrue(daoCreditNote.exists(creditNote.getUID()));
    }

    private void createSeries(String series, ADBusiness business, String uniqueCode) {
        InvoiceSeriesEntity entity = new JPAInvoiceSeriesEntity();
        entity.setBusiness(business);
        entity.setSeries(series);
        entity.setSeriesUniqueCode(uniqueCode);
        DAOInvoiceSeries daoInvoiceSeries = injector.getInstance(DAOInvoiceSeries.class);
        daoInvoiceSeries.create(entity);
    }


    private ADTax createFlatTax(BillyAndorra billyAndorra) {
        final ADTax.Builder taxBuilder = this.injector.getInstance(ADTax.Builder.class);
        taxBuilder.setTaxRate(Tax.TaxRateType.FLAT, new BigDecimal("3.14"))
                .setContextUID(billyAndorra.contexts().andorra().allRegions().getUID())
                .setCode("code1")
                .setDescription("description 1")
                .setValidFrom(new Date(0))
                .setValidTo(new Date(Long.MAX_VALUE))
                .setCurrency(Currency.getInstance("EUR"))
                .setValue(new BigDecimal("5.55"));

        return billyAndorra.taxes().persistence().create(taxBuilder);
    }

    private ADApplication.Builder getAdApplicationBuilder(BillyAndorra billyAndorra) {
        ADContact.Builder contactBuilder = billyAndorra.contacts().builder();
        contactBuilder.setName("Developer 1")
                .setTelephone("2000000001");

        ADApplication.Builder applicationBuilder = billyAndorra.applications().builder();
        applicationBuilder.setDeveloperCompanyName("Developer Company Name")
                .setDeveloperCompanyTaxIdentifier("L-123456-Z", "AD")
                .setName("Billy")
                .setVersion("1.0")
                .addContact(contactBuilder)
                .setMainContact(contactBuilder);
        return applicationBuilder;
    }

    private ADApplication createAdApplication(BillyAndorra billyAndorra, ADApplication.Builder applicationBuilder) {
        return billyAndorra.applications().persistence().create(applicationBuilder);
    }

    private ADBusiness createAdBusiness(BillyAndorra billyAndorra, ADApplication.Builder applicationBuilder) {
        ADContact.Builder contactBuilder = billyAndorra.contacts().builder();
        contactBuilder.setName("CEO 1")
                .setTelephone("200000002");

        ADAddress.Builder addressBuilder = billyAndorra.addresses().builder();
        addressBuilder.setStreetName("Street name 2")
                .setNumber("2")
                .setPostalCode("10000")
                .setCity("Andorra la Vieja")
                .setISOCountry("AD")
                .setDetails("Carrer de la Vall, 6, AD500");

        ADBusiness.Builder businessBuilder = billyAndorra.businesses().builder();
        businessBuilder.addApplication(applicationBuilder)
                .addContact(contactBuilder, true)
                .setMainContactUID(contactBuilder.build().getUID())
                .setName("Business 1")
                .setCommercialName("Business, INC")
                .setFinancialID("L-123456-Z", "AD")
                .setAddress(addressBuilder)
                .setBillingAddress(addressBuilder);

        return billyAndorra.businesses().persistence().create(businessBuilder);
    }

    private ADInvoice createAdInvoice(
		SimpleDateFormat dateFormat,
		BillyAndorra billyAndorra,
		ADBusiness business,
		ADIssuingParams invoiceParameters,
		ADCustomer customer,
		ADProduct product,
		ADProduct productExempt,
		ADProduct productTax) throws ParseException, DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        Date invoiceDate = dateFormat.parse("01-03-2013");

        final ADPayment.Builder paymentBuilder = billyAndorra
                .payments()
                .builder()
                .setPaymentAmount(new BigDecimal("1.1"))
                .setPaymentMethod(PaymentMechanism.CASH)
                .setPaymentDate(invoiceDate);

        ADInvoice.Builder invoiceBuilder = billyAndorra.invoices().builder();

        invoiceBuilder.setSelfBilled(false)
                .setCancelled(false)
                .setBilled(false)
                .setDate(invoiceDate)
                .setSourceId("User 1")
                .addPayment(paymentBuilder)
                .setBusinessUID(business.getUID())
                .setCustomerUID(customer.getUID());

        ADInvoiceEntry.Builder entryBuilder = billyAndorra.invoices().entryBuilder();
        entryBuilder
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billyAndorra.contexts().andorra().allRegions().getUID())
                .setProductUID(product.getUID())
                .setDescription(product.getDescription())
                .setUnitOfMeasure(product.getUnitOfMeasure());

        invoiceBuilder.addEntry(entryBuilder);

        entryBuilder = billyAndorra.invoices().entryBuilder();
        entryBuilder
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10.0"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billyAndorra.contexts().andorra().allRegions().getUID())
                .setProductUID(productExempt.getUID())
                .setDescription(productExempt.getDescription())
                .setUnitOfMeasure(productExempt.getUnitOfMeasure())
                .setTaxExemptionCode("M99")
                .setTaxExemptionReason("reason 1");

        invoiceBuilder.addEntry(entryBuilder);

        entryBuilder = billyAndorra.invoices().entryBuilder();
        entryBuilder
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10.0"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billyAndorra.contexts().andorra().allRegions().getUID())
                .setProductUID(productTax.getUID())
                .setDescription(productTax.getDescription())
                .setUnitOfMeasure(productTax.getUnitOfMeasure())
                .setTaxExemptionCode("M99")
                .setTaxExemptionReason("reason 1");

        invoiceBuilder.addEntry(entryBuilder);

        return billyAndorra.invoices().issue(invoiceBuilder, invoiceParameters);
    }

    private ADCreditNote createAdCreditNote(
		SimpleDateFormat dateFormat,
		BillyAndorra billyAndorra,
		ADBusiness business,
		ADIssuingParams invoiceParameters,
		ADCustomer customer,
		ADProduct product,
		ADInvoice invoice) throws ParseException, DocumentIssuingException, SeriesUniqueCodeNotFilled, DocumentSeriesDoesNotExistException
    {
        Date creditNoteDate = dateFormat.parse("01-03-2013");

        final ADPayment.Builder paymentBuilder = billyAndorra
                .payments()
                .builder()
                .setPaymentAmount(new BigDecimal("1.1"))
                .setPaymentMethod(PaymentMechanism.CASH)
                .setPaymentDate(creditNoteDate);

        ADCreditNote.Builder creditNoteBuilder = billyAndorra.creditNotes().builder();

        creditNoteBuilder.setSelfBilled(false)
                .setCancelled(false)
                .setBilled(false)
                .setDate(creditNoteDate)
                .setSourceId("User 2")
                .addPayment(paymentBuilder)
                .setBusinessUID(business.getUID())
                .setCustomerUID(customer.getUID());

        ADCreditNoteEntry.Builder entryBuilder = billyAndorra.creditNotes().entryBuilder();
        entryBuilder.setAmountType(GenericInvoiceEntryBuilder.AmountType.WITH_TAX)
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billyAndorra.contexts().andorra().allRegions().getUID())
                .setProductUID(product.getUID())
                .setDescription(product.getDescription())
                .setUnitOfMeasure(product.getUnitOfMeasure())
                .setReferenceUID(invoice.getUID())
                .setReason("some reason 1");

        creditNoteBuilder.addEntry(entryBuilder);

        return billyAndorra.creditNotes().issue(creditNoteBuilder, invoiceParameters);
    }

    private ADProduct createAdProduct(BillyAndorra billyAndorra) {
        ADProduct.Builder productBuilder = billyAndorra.products().builder();
        productBuilder.setDescription("product 1")
                .setNumberCode("1")
                .setProductCode("1")
                .setType(Product.ProductType.GOODS)
                .setUnitOfMeasure("kg")
                .setProductGroup("group 1")
                .addTaxUID(billyAndorra.taxes().normal().getUID());

        return billyAndorra.products().persistence().create(productBuilder);
    }

    private ADProduct createAdProductExempt(BillyAndorra billyAndorra) {
        ADProduct.Builder productBuilder = billyAndorra.products().builder();
        productBuilder.setDescription("product 2")
                .setNumberCode("2")
                .setProductCode("2")
                .setType(Product.ProductType.GOODS)
                .setUnitOfMeasure("kg")
                .setProductGroup("group 1")
                .addTaxUID(billyAndorra.taxes().exempt().getUID());

        return billyAndorra.products().persistence().create(productBuilder);
    }

    private ADProduct createAdProductFlat(BillyAndorra billyAndorra, ADTax flatTax) {
        ADProduct.Builder productBuilder = billyAndorra.products().builder();
        productBuilder.setDescription("product 3")
                .setNumberCode("3")
                .setProductCode("3")
                .setType(Product.ProductType.GOODS)
                .setUnitOfMeasure("kg")
                .setProductGroup("group 1")
                .addTaxUID(flatTax.getUID());

        return billyAndorra.products().persistence().create(productBuilder);
    }

    private ADIssuingParams getAdInvoiceIssuingParams() {
        ADIssuingParams invoiceParameters = ADIssuingParams.Util.newInstance();
        invoiceParameters.setInvoiceSeries("A");
        return invoiceParameters;
    }

    private ADIssuingParams getAdCreditNoteIssuingParams() {
        ADIssuingParams invoiceParameters = ADIssuingParams.Util.newInstance();
        invoiceParameters.setInvoiceSeries("B");
        return invoiceParameters;
    }

    private ADCustomer createAdCustomer(BillyAndorra billyAndorra) {
        ADContact.Builder contactBuilder = billyAndorra.contacts().builder();
        contactBuilder.setName("Customer 1")
                .setTelephone("telephone 1");

        ADAddress.Builder addressBuilder = billyAndorra.addresses().builder();
        addressBuilder.setStreetName("Customer 1 street name 1")
                .setNumber("2")
                .setPostalCode("10000")
			    .setCity("Andorra la Vieja")
				.setISOCountry("AD")
				.setDetails("Carrer de la Vall, 6, AD500");

        ADCustomer.Builder customerBuilder = billyAndorra.customers().builder();
        customerBuilder.setName("Customer name 1")
                .setTaxRegistrationNumber("F-123456-Z", "AD")
                .addAddress(addressBuilder, true)
                .setBillingAddress(addressBuilder)
                .setShippingAddress(addressBuilder)
                .setHasSelfBillingAgreement(false)
                .addContact(contactBuilder);

        return billyAndorra.customers().persistence().create(customerBuilder);
    }
}
