/**
 * Copyright (C) 2013 Premium Minds.
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
package com.premiumminds.billy.portugal.test.services.export;

import java.io.PrintStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.premiumminds.billy.core.services.entities.Product.ProductType;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTBusiness;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCreditNote;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTCustomer;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTProduct;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTRegionContext;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSimpleInvoice;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTSupplier;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTApplicationEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTCustomerEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTProductEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTSupplierEntity;
import com.premiumminds.billy.portugal.services.entities.PTAddress;
import com.premiumminds.billy.portugal.services.entities.PTApplication;
import com.premiumminds.billy.portugal.services.entities.PTContact;
import com.premiumminds.billy.portugal.services.entities.PTGenericInvoice.TYPE;
import com.premiumminds.billy.portugal.services.export.saftpt.PTSAFTFileGenerator;
import com.premiumminds.billy.portugal.test.PTPersistencyAbstractTest;
import com.premiumminds.billy.portugal.test.util.PTAddressTestUtil;
import com.premiumminds.billy.portugal.test.util.PTApplicationTestUtil;
import com.premiumminds.billy.portugal.test.util.PTBusinessTestUtil;
import com.premiumminds.billy.portugal.test.util.PTContactTestUtil;
import com.premiumminds.billy.portugal.test.util.PTCreditNoteTestUtil;
import com.premiumminds.billy.portugal.test.util.PTCustomerTestUtil;
import com.premiumminds.billy.portugal.test.util.PTInvoiceTestUtil;
import com.premiumminds.billy.portugal.test.util.PTProductTestUtil;
import com.premiumminds.billy.portugal.test.util.PTSimpleInvoiceTestUtil;
import com.premiumminds.billy.portugal.test.util.PTSupplierTestUtil;
import com.premiumminds.billy.portugal.util.GenerateHash;
import com.premiumminds.billy.portugal.util.KeyGenerator;

public class SAFTExportTest extends PTPersistencyAbstractTest {

	private static final String PRIVATE_KEY_DIR = "src/test/resources/keys/private.pem";
	private static final String SAFT_OUTPUT = "src/test/resources/documents/";

	private static final String BUSINESS_UID = "BUSINESS_UID";
	private static final String CUSTOMER_UID = "CUSTOMER_UID";
	private static final String SUPPLIER_UID = "SUPPLIER_UID";
	private static final String PRODUCT_UID1 = "PRODUCT_UID1";
	private static final String PRODUCT_UID2 = "PRODUCT_UID2";
	private static final String INVOICE_UID = "INVOICE_UID";
	private static final String SIMPLE_INVOICE_UID = "SIMPLE_INVOICE_UID";
	private static final int MAX_INVOICES = 3;

	@Test
	public void doTest() {
		try {
			Config c = new Config();
			PTContactTestUtil contact = new PTContactTestUtil(injector);
			PTAddressTestUtil address = new PTAddressTestUtil(injector);
			PTApplicationTestUtil application = new PTApplicationTestUtil(
					injector);
			PTBusinessTestUtil business = new PTBusinessTestUtil(injector);
			PTCustomerTestUtil customer = new PTCustomerTestUtil(injector);
			PTSupplierTestUtil supplier = new PTSupplierTestUtil(injector);
			PTProductTestUtil product = new PTProductTestUtil(injector);
			PTInvoiceTestUtil invoice = new PTInvoiceTestUtil(injector);
			PTCreditNoteTestUtil creditNote = new PTCreditNoteTestUtil(injector);
			PTSimpleInvoiceTestUtil simpleInvoice = new PTSimpleInvoiceTestUtil(
					injector);

			DAOPTRegionContext daoPTRegionContext = injector
					.getInstance(DAOPTRegionContext.class);
			PTRegionContextEntity myContext = (PTRegionContextEntity) daoPTRegionContext
					.get(c.getUID(Config.Key.Context.Portugal.UUID));

			/* ADDRESSES */
			// DAOPTAddress daoPTAddress = injector
			// .getInstance(DAOPTAddress.class);
			PTAddress.Builder addressBuilder1 = address.getAddressBuilder(
					"Av. Republica", "Nº 3 - 3º Esq.",
					"Av. Republica Nº 3 - 3º Esq.", "", "Lisboa", "1700-232",
					"", "PT");
			PTAddress.Builder addressBuilder2 = address.getAddressBuilder(
					"Av. Liberdade", "Nº 4 - 5º Dir.",
					"Av. Liberdade, Nº 4 - 5º Dir.", "", "Lisboa", "1500-123",
					"", "PT");
			PTAddress.Builder addressBuilder3 = address.getAddressBuilder(
					"Campo Grande", "Condomínio X", "Lote 20, Andar 3", "",
					"Lisboa", "1000-253", "", "PT");

			/* CONTACTS */
			// DAOPTContact daoPTContact = injector
			// .getInstance(DAOPTContact.class);
			PTContact.Builder contactBuilder = contact.getContactBuilder(
					"My Business", "299999999", "999999999", "299999998",
					"mybusiness@email.me", "http://www.mybusiness.web");

			PTContact.Builder contactBuilder2 = contact.getContactBuilder("Zé",
					"299999991", "999999991", "299999992", "maildoze@email.me",
					"http://www.zenaweb.web");

			PTContact.Builder contactBuilder3 = contact.getContactBuilder(
					"YourSupplier", "299999993", "999999993", "299999994",
					"maildoyourbusiness@email.me",
					"http://www.yourbusinessnaweb.web");

			/* APPLICATION */
			// DAOPTApplication daoPTApplication = injector
			// .getInstance(DAOPTApplication.class);
			PTApplication.Builder applicationBuilder = application
					.getApplicationBuilder("APP", "1.0", "My Business",
							"523456789", "hhtp://www.app.mybusiness.web", 1,
							"http://here", contactBuilder);
			PTApplicationEntity applicationEntity = (PTApplicationEntity) applicationBuilder
					.build();

			/* BUSINESS */
			DAOPTBusiness daoPTBusiness = injector
					.getInstance(DAOPTBusiness.class);
			PTBusinessEntity businessEntity = business.getBusinessEntity(
					BUSINESS_UID, myContext.getUID(), contactBuilder,
					addressBuilder1, applicationBuilder);
			daoPTBusiness.create(businessEntity);

			/* CUSTOMERS */
			DAOPTCustomer daoPTCustomer = injector
					.getInstance(DAOPTCustomer.class);
			PTCustomerEntity customerEntity = customer.getCustomerEntity(
					CUSTOMER_UID, "Zé", "26949843873", false, addressBuilder2,
					contactBuilder2);
			daoPTCustomer.create(customerEntity);

			PTCustomerEntity genericCustomerEntity = (PTCustomerEntity) daoPTCustomer
					.get(c.getUID(Config.Key.Customer.Generic.UUID));

			/* SUPPLIERS */
			DAOPTSupplier daoPTSupplier = injector
					.getInstance(DAOPTSupplier.class);

			PTSupplierEntity supplierEntity = supplier.getSupplierEntity(
					SUPPLIER_UID, "YourSupplier", "5324532453", false,
					addressBuilder3, contactBuilder3);
			daoPTSupplier.create(supplierEntity);

			/* TAXES */
			DAOPTTax daoPTTax = injector.getInstance(DAOPTTax.class);

			/* PRODUCTS */
			DAOPTProduct daoPTProduct = injector
					.getInstance(DAOPTProduct.class);

			PTProductEntity productEntity1 = product.getProductEntity(
					"PRODUCT_UID1", "124233465", "Kg", "34254567", "Produto",
					ProductType.GOODS);
			PTProductEntity productEntity2 = product.getProductEntity(
					"PRODUCT_UID2", "243532453", "hh:mm:ss", "13423534",
					"Estacionamento", ProductType.SERVICE);

			// INVOICES
			DAOPTInvoice daoPTInvoice = injector
					.getInstance(DAOPTInvoice.class);

			KeyGenerator keyGenerator = new KeyGenerator(PRIVATE_KEY_DIR);
			PrivateKey privateKey = keyGenerator.getPrivateKey();
			PublicKey publicKey = keyGenerator.getPublicKey();
			String prevHash = null;

			List<PTInvoiceEntity> invoices = new ArrayList<PTInvoiceEntity>();
			for (int i = 1; i < MAX_INVOICES; i++) {
				PTInvoiceEntity invoiceEntity = invoice.getInvoiceEntity(
						TYPE.FT,
						"A",
						INVOICE_UID + i,
						i,
						new Date().toString(),
						BUSINESS_UID,
						(i % 2 == 0) ? CUSTOMER_UID : c.getUID(
								Config.Key.Customer.Generic.UUID).getValue(),
						Arrays.asList(PRODUCT_UID1, PRODUCT_UID2, PRODUCT_UID1,
								PRODUCT_UID2, PRODUCT_UID1, PRODUCT_UID2));
				prevHash = GenerateHash.generateHash(privateKey, publicKey,
						invoiceEntity.getDate(),
						invoiceEntity.getCreateTimestamp(),
						invoiceEntity.getNumber(),
						invoiceEntity.getAmountWithTax(), prevHash);
				invoiceEntity.setHash(prevHash);

				daoPTInvoice.create(invoiceEntity);
				invoices.add(invoiceEntity);
			}

			// SIMPLE INVOICE
			DAOPTSimpleInvoice daoPTSimpleInvoice = injector
					.getInstance(DAOPTSimpleInvoice.class);
			PTSimpleInvoiceEntity simpleInvoiceEntity = simpleInvoice
					.getInvoiceEntity(TYPE.FS, "S", SIMPLE_INVOICE_UID, 1,
							new Date().toString(), BUSINESS_UID, CUSTOMER_UID,
							Arrays.asList(PRODUCT_UID1, PRODUCT_UID2));
			simpleInvoiceEntity.setHash(GenerateHash.generateHash(privateKey,
					publicKey, simpleInvoiceEntity.getDate(),
					simpleInvoiceEntity.getCreateTimestamp(),
					simpleInvoiceEntity.getNumber(),
					simpleInvoiceEntity.getAmountWithTax(), null));
			daoPTSimpleInvoice.create(simpleInvoiceEntity);

			// CREDIT NOTE
			DAOPTCreditNote daoPTCreditNote = injector
					.getInstance(DAOPTCreditNote.class);
			PTCreditNoteEntity creditNoteEntity = creditNote
					.getCreditNoteEntity(TYPE.NC, businessEntity.getUID()
							.getValue(), customerEntity.getUID().getValue(),
							productEntity1.getUID().getValue(), invoices.get(0)
									.getUID().getValue());
			creditNoteEntity.setHash(GenerateHash.generateHash(privateKey,
					publicKey, creditNoteEntity.getDate(),
					creditNoteEntity.getCreateTimestamp(),
					creditNoteEntity.getNumber(),
					creditNoteEntity.getAmountWithTax(), null));
			daoPTCreditNote.create(creditNoteEntity);

			PTSAFTFileGenerator generator = new PTSAFTFileGenerator();

			Calendar calendar = Calendar.getInstance();
			calendar.set(2013, 1, 1);

			PrintStream stream = new PrintStream(SAFT_OUTPUT + "SAFT.xml");
			generator.generateSAFTFile(stream, businessEntity,
					applicationEntity, "1234", calendar.getTime(), new Date(),
					daoPTCustomer, daoPTSupplier, daoPTProduct, daoPTTax,
					daoPTRegionContext, daoPTInvoice, daoPTCreditNote);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
