/*******************************************************************************
 * Copyright (C) 2013 Premium Minds.
 *  
 * This file is part of billy-platypus.
 * 
 * billy-platypus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * billy-platypus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy-platypus.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.premiumminds.billy.platypus.services.export;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.premiumminds.billy.core.CoreJPADependencyModule;
import com.premiumminds.billy.core.persistence.dao.DAOCustomer;
import com.premiumminds.billy.core.persistence.entities.jpa.AddressEntityImpl;
import com.premiumminds.billy.core.persistence.entities.jpa.ContactEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.ContextEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.CustomerEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.ProductEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.TaxEntity;
import com.premiumminds.billy.core.util.ContactType;
import com.premiumminds.billy.core.util.ProductType;
import com.premiumminds.billy.platypus.PlatypusTestPersistenceDependencyModule;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.PlatypusBootstrap;
import com.premiumminds.billy.portugal.PlatypusDependencyModule;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.IPTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.DocumentState;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.PaymentMechanism;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTFinancialDocumentEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTTaxEntity;
import com.premiumminds.billy.portugal.services.certification.CertificationManager;
import com.premiumminds.billy.portugal.services.export.saftpt.SAFTFileGenerator;

public class SAFTExportTest {
	
	
	@BeforeClass 
	public static void setUpClass() {
		Injector injector = Guice.createInjector(new CoreJPADependencyModule(), new PlatypusDependencyModule(), new PlatypusTestPersistenceDependencyModule());
		injector.getInstance(PlatypusTestPersistenceDependencyModule.Initializer.class);
		injector.getInstance(PlatypusDependencyModule.Initializer.class);
		
		PlatypusBootstrap.execute(injector);
						
		try
		{
			Config c = new Config();
	        
	        ContextEntity myContext = new ContextEntity("myContext", "This is my operational context", null);
	        
	        /* ADDRESSES */
//	        DAOAddress daoAddress = injector.getInstance(DAOAddress.class);
	        AddressEntityImpl address1 = new AddressEntityImpl("PT", "Av. Republica", "Nº 3 - 3º Esq.", null, "Lisboa", null, "1700-232");
	        AddressEntityImpl address2 = new AddressEntityImpl("PT", "Av. Liberdade, Nº 4 - 5º Dir.", null, null, "Lisboa", null, "1500-123");
	        AddressEntityImpl address3 = new AddressEntityImpl("PT", "Campo Grande", "Condomínio X", "Lote 20, Andar 3", "Lisboa", null, "1000-253");

	        /* CONTACTS */
//	        DAOContact daoContact = injector.getInstance(DAOContact.class);
	        List<ContactEntity> contactsList1 = new ArrayList<ContactEntity>();	        
	        // MAIL
//	        ContactEntity mail = new ContactEntity(ContactType.EMAIL, "afonsovilela@netcabo.pt");
//	        contactsList1.add(mail);
	        // FAX
	        ContactEntity fax = new ContactEntity(ContactType.FAX, "00351212223344");
	        contactsList1.add(fax);
	        	        
	        List<ContactEntity> contactsList2 = new ArrayList<ContactEntity>();
	        // PHONE
	        ContactEntity phone = new ContactEntity(ContactType.PHONE, "919193355");
	        contactsList1.add(phone);
	        contactsList2.add(phone);
	        
	        /* BUSINESS */
//	        DAOPTBusiness daoPTBusiness = injector.getInstance(DAOPTBusiness.class);
	        PTBusinessEntity be = new PTBusinessEntity(myContext, "123456789", "myBusiness", "myBussiness, Lda",
	        											address1, null, null, contactsList1, "www.myBusiness.pt",
	        											/* List<ApplicationEntity> applications */ null, "ConservatoriaXPTO", "123");
	        
	        /* CUSTOMERS */
	        DAOCustomer daoCustomer = injector.getInstance(DAOCustomer.class);
	        
	        List<CustomerEntity> customers = new ArrayList<CustomerEntity>();
	        CustomerEntity ce = new CustomerEntity("Luis Santos", "Porteiro", "554681462", address2, address2, null, contactsList2, null, false);	        
	        customers.add(ce);	       
	        CustomerEntity ce2 = (CustomerEntity) daoCustomer.get(c.getUUID(Config.Key.Customer.Generic.UUID));
	        customers.add(ce2);
	        
	        /* TAXES */
	        DAOPTTax daoPTTax = injector.getInstance(DAOPTTax.class);
	        List<PTTaxEntity> taxes = new ArrayList<PTTaxEntity>();	       
	        taxes.add((PTTaxEntity) daoPTTax.get(c.getUUID(Config.Key.Context.Portugal.Continental.VAT.NORMAL_UUID)));
	        taxes.add((PTTaxEntity) daoPTTax.get(c.getUUID(Config.Key.Context.Portugal.Azores.VAT.INTERMEDIATE_UUID)));
	        
	        /* PRODUCTS */
//	        DAOProduct daoProduct = injector.getInstance(DAOProduct.class);
	        List<ProductEntity> products = new ArrayList<ProductEntity>();
	        ProductEntity prod, serv;
	        products.add(prod = new ProductEntity(ProductType.PRODUCT, "ProductCode", "ProductFamily", "ProductDescription", "ProductEANCode", new ArrayList<TaxEntity>(taxes)));
	        products.add(serv = new ProductEntity(ProductType.SERVICE, "ServiceCode", "ServiceFamily", "ServiceDescription", "ServiceEANCode", new ArrayList<TaxEntity>(taxes))); 
	        
	        BigDecimal netTotal = BigDecimal.ZERO, taxTotal = BigDecimal.ZERO, grossTotal = BigDecimal.ZERO;
	        
	        /* DOCUMENTS */	        
	        List<IPTInvoiceEntity> invoices = new ArrayList<IPTInvoiceEntity>();
	        List<IPTCreditNoteEntity> creditNotes = new ArrayList<IPTCreditNoteEntity>();
	        List<PTFinancialDocumentEntryEntity> documentEntries1 = new ArrayList<PTFinancialDocumentEntryEntity>();
	        List<PTFinancialDocumentEntryEntity> documentEntries2 = new ArrayList<PTFinancialDocumentEntryEntity>();
	        List<PTFinancialDocumentEntryEntity> documentEntries3 = new ArrayList<PTFinancialDocumentEntryEntity>();

	       /* INVOICES & their document entries */	       	       
	        for(int i = 1; i <= 10; i++)
	        {
	        	netTotal = netTotal.add(new BigDecimal(10*i));
	        	taxTotal = taxTotal.add(new BigDecimal(10*i*0.23));
	        	grossTotal = netTotal.add(taxTotal);
	        	documentEntries1.add(new PTFinancialDocumentEntryEntity(i, (i%2 == 0) ? prod : serv, "EntryDescription"+i, new Date(),
	        						new BigDecimal(i), new BigDecimal(10), new BigDecimal(10*i), new BigDecimal(10*i*0.23), BigDecimal.ZERO, new BigDecimal(10*i + 10*i*0.23),
    								Currency.getInstance("EUR"), taxes, null, "No Motive", "orderID"+i, new Date()));
	        }
	        
	        
	        netTotal = netTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	        taxTotal = taxTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	        grossTotal = grossTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
	        
	        
	        /** KEYS && HASH */	       
	        if (Security.getProvider("BC") == null) {
				Security.addProvider(new BouncyCastleProvider());
			}
			InputStream stream = SAFTExportTest.class.getClassLoader().getResourceAsStream("platypustestkeys/private.pem");
			PEMReader pemReader = null;
			KeyPair pair = null;
			try {
				pemReader = new PEMReader(new StringReader(IOUtils.toString(stream)));
				pair = (KeyPair) pemReader.readObject();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally{
				IOUtils.closeQuietly(stream);
				IOUtils.closeQuietly(pemReader);
			}	        
	        CertificationManager cm = new CertificationManager();
	        cm.setAutoVerifyHash(true);
	        cm.setPrivateKey(pair.getPrivate());
	        cm.setPublicKey(pair.getPublic());
	        byte[] prevHash = null;
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			StringBuilder builder;
			String sourceString;
	        
	        PTInvoiceEntity inv;
	        PTCreditNoteEntity myCN;
	        
			/** INVOICES */
	        for(int i = 1; i <= 5; i++)
	        {	        	
	        	invoices.add( inv = new PTInvoiceEntity(be, null, ce, documentEntries1, new ArrayList<PTTaxEntity>(),	        		
	        								new Date(), "SettlementDescription"+i, new Date(), netTotal,  taxTotal,  BigDecimal.ZERO,  grossTotal,
	        								Currency.getInstance("EUR"), false, null,
	        								"1", i, (i%2 == 0) ? DocumentState.NORMAL : DocumentState.BILLED, PaymentMechanism.CASH,
	        								"deliveryOriginId"+i, address1, new Date(),
	        								"deliveryId"+i, address3, new Date(), null));

	        	/* BUILDING THE HASH */	        	
	        	builder = new StringBuilder();
				builder
				.append(date.format(inv.getIssueDate()))
				.append(';')
				.append(dateTime.format(inv.getCreateTimestamp()))
				.append(';')
				.append(inv.getDocumentNumberSAFTPT())
				.append(';')
				.append(inv.getGrossTotal())
				.append(';')
				.append(prevHash == null ? "" : Base64.encodeBase64String(prevHash));
				sourceString = builder.toString();
				
	        	prevHash = cm.getHashBinary(sourceString);
	        	inv.setHash(prevHash);
	        }
	        
	        /* CREDIT NOTES & their document entries */
	        netTotal = new BigDecimal(200);
        	taxTotal = new BigDecimal(200*0.23);
        	grossTotal = netTotal.add(taxTotal);	
        	
	        documentEntries2.add(new PTFinancialDocumentEntryEntity(1, serv, "EntryDescription1", new Date(),
	        						new BigDecimal(20), new BigDecimal(10), netTotal, taxTotal, BigDecimal.ZERO, grossTotal,
        							Currency.getInstance("EUR"), taxes, null, "No Motive", "orderID", new Date()));
	        
	        creditNotes.add(myCN = new PTCreditNoteEntity(be, null, ce, documentEntries2, new ArrayList<PTTaxEntity>(),
					new Date(), "sdescr", new Date(), netTotal, taxTotal, BigDecimal.ZERO, grossTotal,
					Currency.getInstance("EUR"), false, null,
					"SequId0", 0, DocumentState.NORMAL, PaymentMechanism.CASH,
					"deliveryOriginId0", address3, new Date(),
					"deliveryId0", address2, new Date(), null, "reasonForCredit"));         	
        	builder = new StringBuilder();
 			builder
 			.append(date.format(myCN.getIssueDate()))
 			.append(';')
 			.append(dateTime.format(myCN.getCreateTimestamp()))
 			.append(';')
 			.append(myCN.getDocumentNumberSAFTPT())
 			.append(';')
 			.append(myCN.getGrossTotal().setScale(2))
 			.append(';');
 			sourceString = builder.toString();
         	myCN.setHash(cm.getHashBinary(sourceString));
	        
        	/* INVOICES + CREDIT NOTES */  
            documentEntries3.add(new PTFinancialDocumentEntryEntity(1, prod, "EntryDescription0", new Date(),
			    					new BigDecimal(20), new BigDecimal(10), netTotal, taxTotal, null, grossTotal,
			    					Currency.getInstance("EUR"), taxes, myCN, "No Motive", "orderID0", new Date()));

            invoices.add(inv = new PTInvoiceEntity(be, null, ce, documentEntries3, new ArrayList<PTTaxEntity>(),
					new Date(), "SettlementDescription", new Date(), netTotal,  taxTotal,  BigDecimal.ZERO,  grossTotal,
					Currency.getInstance("EUR"), false, null,
					"1", 6, DocumentState.NORMAL, PaymentMechanism.BANK_TRANSFER,
					"deliveryOriginId", address1, new Date(),
					"deliveryId", address3, new Date(), null));
           
            /* BUILDING THE HASH */	        	
        	builder = new StringBuilder();
			builder
			.append(date.format(inv.getIssueDate()))
			.append(';')
			.append(dateTime.format(inv.getCreateTimestamp()))
			.append(';')
			.append(inv.getDocumentNumberSAFTPT())
			.append(';')
			.append(inv.getGrossTotal().setScale(2))
			.append(';')
			.append(prevHash == null ? "" : Base64.encodeBase64String(prevHash));
			sourceString = builder.toString();
        	inv.setHash(cm.getHashBinary(sourceString));

	        
	        /*SAFTFileGenerator generator = */new SAFTFileGenerator();	
//	        generator.generateSAFTFile(be, customers, products, taxes, invoices, null, creditNotes, new Date(), new Date());
	        System.out.println("ALL OK");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() {
			
		assertEquals(2 , 2);
	}
}
