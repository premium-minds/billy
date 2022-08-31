package com.premiumminds.billy.spain.its;

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
import com.premiumminds.billy.spain.BillySpain;
import com.premiumminds.billy.spain.SpainBootstrap;
import com.premiumminds.billy.spain.SpainDependencyModule;
import com.premiumminds.billy.spain.SpainPersistenceDependencyModule;
import com.premiumminds.billy.spain.persistence.dao.DAOESCreditNote;
import com.premiumminds.billy.spain.persistence.dao.DAOESInvoice;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.entities.ESAddress;
import com.premiumminds.billy.spain.services.entities.ESApplication;
import com.premiumminds.billy.spain.services.entities.ESBusiness;
import com.premiumminds.billy.spain.services.entities.ESContact;
import com.premiumminds.billy.spain.services.entities.ESCreditNote;
import com.premiumminds.billy.spain.services.entities.ESCreditNoteEntry;
import com.premiumminds.billy.spain.services.entities.ESCustomer;
import com.premiumminds.billy.spain.services.entities.ESInvoice;
import com.premiumminds.billy.spain.services.entities.ESInvoiceEntry;
import com.premiumminds.billy.spain.services.entities.ESPayment;
import com.premiumminds.billy.spain.services.entities.ESProduct;
import com.premiumminds.billy.spain.services.entities.ESTax;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvoiceIT {

    private Injector injector;

    @Test
    public void test() throws Exception {

        injector = Guice.createInjector(new SpainDependencyModule(),
                new SpainPersistenceDependencyModule("BillySpainTestPersistenceUnit"));
        injector.getInstance(SpainDependencyModule.Initializer.class);
        injector.getInstance(SpainPersistenceDependencyModule.Initializer.class);
        SpainBootstrap.execute(injector);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        BillySpain billySpain = new BillySpain(injector);
        ESIssuingParams invoiceParameters = getEsInvoiceIssuingParams();
        ESIssuingParams creditNoteParameters = getEsCreditNoteIssuingParams();

        ESApplication.Builder applicationBuilder = getEsApplicationBuilder(billySpain);
        ESBusiness business = createEsBusiness(billySpain, applicationBuilder);
        ESCustomer customer = createEsCustomer(billySpain);

        createSeries(invoiceParameters.getInvoiceSeries(), business, "CCCC2345");
        createSeries(creditNoteParameters.getInvoiceSeries(), business, "CCCC2346");

        final ESTax flatTax = createFlatTax(billySpain);

        ESProduct product = createEsProduct(billySpain);
        ESProduct productExempt = createEsProductExempt(billySpain);
        ESProduct productFlat = createEsProductFlat(billySpain, flatTax);
        ESInvoice invoice = createEsInvoice(dateFormat, billySpain, business, invoiceParameters, customer, product, productExempt, productFlat);
        ESCreditNote creditNote = createEsCreditNote(dateFormat,
                billySpain,
                business,
                creditNoteParameters,
                customer,
                product,
                invoice);

        final DAOESInvoice daoInvoice = injector.getInstance(DAOESInvoice.class);
        final DAOESCreditNote daoCreditNote = injector.getInstance(DAOESCreditNote.class);
        assertTrue(daoInvoice.exists(invoice.getUID()));
        assertTrue(daoCreditNote.exists(creditNote.getUID()));
    }

    private void createSeries(String series, ESBusiness business, String uniqueCode) {
        InvoiceSeriesEntity entity = new JPAInvoiceSeriesEntity();
        entity.setBusiness(business);
        entity.setSeries(series);
        entity.setSeriesUniqueCode(uniqueCode);
        DAOInvoiceSeries daoInvoiceSeries = injector.getInstance(DAOInvoiceSeries.class);
        daoInvoiceSeries.create(entity);
    }


    private ESTax createFlatTax(BillySpain billySpain) {
        final ESTax.Builder taxBuilder = this.injector.getInstance(ESTax.Builder.class);
        taxBuilder.setTaxRate(Tax.TaxRateType.FLAT, new BigDecimal("3.14"))
                .setContextUID(billySpain.contexts().continent().allContinentRegions().getUID())
                .setCode("code1")
                .setDescription("description 1")
                .setValidFrom(new Date(0))
                .setValidTo(new Date(Long.MAX_VALUE))
                .setCurrency(Currency.getInstance("EUR"))
                .setValue(new BigDecimal("5.55"));

        return billySpain.taxes().persistence().create(taxBuilder);
    }

    private ESApplication.Builder getEsApplicationBuilder(BillySpain billySpain) {
        ESContact.Builder contactBuilder = billySpain.contacts().builder();
        contactBuilder.setName("Developer 1")
                .setTelephone("2000000001");

        ESApplication.Builder applicationBuilder = billySpain.applications().builder();
        applicationBuilder.setDeveloperCompanyName("Developer Company Name")
                .setDeveloperCompanyTaxIdentifier("1000000001")
                .setName("Billy")
                .setVersion("1.0")
                .addContact(contactBuilder)
                .setMainContact(contactBuilder);
        return applicationBuilder;
    }

    private ESApplication createEsApplication(BillySpain billySpain, ESApplication.Builder applicationBuilder) {
        return billySpain.applications().persistence().create(applicationBuilder);
    }

    private ESBusiness createEsBusiness(BillySpain billySpain, ESApplication.Builder applicationBuilder) {
        ESContact.Builder contactBuilder = billySpain.contacts().builder();
        contactBuilder.setName("CEO 1")
                .setTelephone("200000002");

        ESAddress.Builder addressBuilder = billySpain.addresses().builder();
        addressBuilder.setStreetName("Street name 2")
                .setNumber("2")
                .setPostalCode("10000")
                .setCity("Madrid")
                .setISOCountry("ES")
                .setDetails("C. de Bailén, s/n, 28071 Madrid");

        ESBusiness.Builder businessBuilder = billySpain.businesses().builder();
        businessBuilder.addApplication(applicationBuilder)
                .addContact(contactBuilder, true)
                .setMainContactUID(contactBuilder.build().getUID())
                .setName("Business 1")
                .setCommercialName("Business, INC")
                .setFinancialID("A58250606", "ES")
                .setAddress(addressBuilder)
                .setBillingAddress(addressBuilder);

        return billySpain.businesses().persistence().create(businessBuilder);
    }

    private ESInvoice createEsInvoice(SimpleDateFormat dateFormat,
                                      BillySpain billySpain,
                                      ESBusiness business,
                                      ESIssuingParams invoiceParameters,
                                      ESCustomer customer,
                                      ESProduct product,
                                      ESProduct productExempt,
                                      ESProduct productTax) throws ParseException, DocumentIssuingException {
        Date invoiceDate = dateFormat.parse("01-03-2013");

        final ESPayment.Builder paymentBuilder = billySpain
                .payments()
                .builder()
                .setPaymentAmount(new BigDecimal("1.1"))
                .setPaymentMethod(PaymentMechanism.CASH)
                .setPaymentDate(invoiceDate);

        ESInvoice.Builder invoiceBuilder = billySpain.invoices().builder();

        invoiceBuilder.setSelfBilled(false)
                .setCancelled(false)
                .setBilled(false)
                .setDate(invoiceDate)
                .setSourceId("User 1")
                .addPayment(paymentBuilder)
                .setBusinessUID(business.getUID())
                .setCustomerUID(customer.getUID());

        ESInvoiceEntry.Builder entryBuilder = billySpain.invoices().entryBuilder();
        entryBuilder
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billySpain.contexts().continent().allContinentRegions().getUID())
                .setProductUID(product.getUID())
                .setDescription(product.getDescription())
                .setUnitOfMeasure(product.getUnitOfMeasure());

        invoiceBuilder.addEntry(entryBuilder);

        entryBuilder = billySpain.invoices().entryBuilder();
        entryBuilder
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10.0"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billySpain.contexts().continent().allContinentRegions().getUID())
                .setProductUID(productExempt.getUID())
                .setDescription(productExempt.getDescription())
                .setUnitOfMeasure(productExempt.getUnitOfMeasure())
                .setTaxExemptionCode("M99")
                .setTaxExemptionReason("reason 1");

        invoiceBuilder.addEntry(entryBuilder);

        entryBuilder = billySpain.invoices().entryBuilder();
        entryBuilder
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10.0"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billySpain.contexts().continent().allContinentRegions().getUID())
                .setProductUID(productTax.getUID())
                .setDescription(productTax.getDescription())
                .setUnitOfMeasure(productTax.getUnitOfMeasure())
                .setTaxExemptionCode("M99")
                .setTaxExemptionReason("reason 1");

        invoiceBuilder.addEntry(entryBuilder);

        return billySpain.invoices().issue(invoiceBuilder, invoiceParameters);
    }

    private ESCreditNote createEsCreditNote(SimpleDateFormat dateFormat,
                                            BillySpain billySpain,
                                            ESBusiness business,
                                            ESIssuingParams invoiceParameters,
                                            ESCustomer customer,
                                            ESProduct product,
                                            ESInvoice invoice) throws ParseException, DocumentIssuingException {
        Date creditNoteDate = dateFormat.parse("01-03-2013");

        final ESPayment.Builder paymentBuilder = billySpain
                .payments()
                .builder()
                .setPaymentAmount(new BigDecimal("1.1"))
                .setPaymentMethod(PaymentMechanism.CASH)
                .setPaymentDate(creditNoteDate);

        ESCreditNote.Builder creditNoteBuilder = billySpain.creditNotes().builder();

        creditNoteBuilder.setSelfBilled(false)
                .setCancelled(false)
                .setBilled(false)
                .setDate(creditNoteDate)
                .setSourceId("User 2")
                .addPayment(paymentBuilder)
                .setBusinessUID(business.getUID())
                .setCustomerUID(customer.getUID());

        ESCreditNoteEntry.Builder entryBuilder = billySpain.creditNotes().entryBuilder();
        entryBuilder.setAmountType(GenericInvoiceEntryBuilder.AmountType.WITH_TAX)
                .setCurrency(Currency.getInstance("EUR"))
                .setQuantity(new BigDecimal("10"))
                .setTaxPointDate(dateFormat.parse("01-02-2013"))
                .setUnitAmount(GenericInvoiceEntryBuilder.AmountType.WITH_TAX, new BigDecimal("100"))
                .setContextUID(billySpain.contexts().continent().allContinentRegions().getUID())
                .setProductUID(product.getUID())
                .setDescription(product.getDescription())
                .setUnitOfMeasure(product.getUnitOfMeasure())
                .setReferenceUID(invoice.getUID())
                .setReason("some reason 1");

        creditNoteBuilder.addEntry(entryBuilder);

        return billySpain.creditNotes().issue(creditNoteBuilder, invoiceParameters);
    }

    private ESProduct createEsProduct(BillySpain billySpain) {
        ESProduct.Builder productBuilder = billySpain.products().builder();
        productBuilder.setDescription("product 1")
                .setNumberCode("1")
                .setProductCode("1")
                .setType(Product.ProductType.GOODS)
                .setUnitOfMeasure("kg")
                .setProductGroup("group 1")
                .addTaxUID(billySpain.taxes().continent().normal().getUID());

        return billySpain.products().persistence().create(productBuilder);
    }

    private ESProduct createEsProductExempt(BillySpain billySpain) {
        ESProduct.Builder productBuilder = billySpain.products().builder();
        productBuilder.setDescription("product 2")
                .setNumberCode("2")
                .setProductCode("2")
                .setType(Product.ProductType.GOODS)
                .setUnitOfMeasure("kg")
                .setProductGroup("group 1")
                .addTaxUID(billySpain.taxes().exempt().getUID());

        return billySpain.products().persistence().create(productBuilder);
    }

    private ESProduct createEsProductFlat(BillySpain billySpain, ESTax flatTax) {
        ESProduct.Builder productBuilder = billySpain.products().builder();
        productBuilder.setDescription("product 3")
                .setNumberCode("3")
                .setProductCode("3")
                .setType(Product.ProductType.GOODS)
                .setUnitOfMeasure("kg")
                .setProductGroup("group 1")
                .addTaxUID(flatTax.getUID());

        return billySpain.products().persistence().create(productBuilder);
    }

    private ESIssuingParams getEsInvoiceIssuingParams() {
        ESIssuingParams invoiceParameters = ESIssuingParams.Util.newInstance();
        invoiceParameters.setInvoiceSeries("A");
        return invoiceParameters;
    }

    private ESIssuingParams getEsCreditNoteIssuingParams() {
        ESIssuingParams invoiceParameters = ESIssuingParams.Util.newInstance();
        invoiceParameters.setInvoiceSeries("B");
        return invoiceParameters;
    }

    private ESCustomer createEsCustomer(BillySpain billySpain) {
        ESContact.Builder contactBuilder = billySpain.contacts().builder();
        contactBuilder.setName("Customer 1")
                .setTelephone("telephone 1");

        ESAddress.Builder addressBuilder = billySpain.addresses().builder();
        addressBuilder.setStreetName("Customer 1 street name 1")
                .setNumber("2")
                .setPostalCode("10000")
                .setCity("Madrid")
                .setISOCountry("ES")
                .setDetails("C. de Bailén, s/n, 28071 Madrid");

        ESCustomer.Builder customerBuilder = billySpain.customers().builder();
        customerBuilder.setName("Customer name 1")
                .setTaxRegistrationNumber("12825060F", "ES")
                .addAddress(addressBuilder, true)
                .setBillingAddress(addressBuilder)
                .setShippingAddress(addressBuilder)
                .setHasSelfBillingAgreement(false)
                .addContact(contactBuilder);

        return billySpain.customers().persistence().create(customerBuilder);
    }
}
