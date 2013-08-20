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

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
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
import com.premiumminds.billy.portugal.services.export.exceptions.SAFTPTExportException;
import com.premiumminds.billy.portugal.services.export.saftpt.PTSAFTFileGenerator;
import com.premiumminds.billy.portugal.test.PTAbstractTest;
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

	private static final String	PRIVATE_KEY_DIR	= "src/test/resources/keys/private.pem";
	private static final String	SAFT_OUTPUT		= System.getProperty("java.io.tmpdir")
														+ "/";

	private static final int	MAX_INVOICES	= 3;

	@Test
	public void doTest() throws Exception {
		Config c = new Config();
		PTContactTestUtil contact = new PTContactTestUtil(
				PTAbstractTest.injector);
		PTAddressTestUtil address = new PTAddressTestUtil(
				PTAbstractTest.injector);
		PTApplicationTestUtil application = new PTApplicationTestUtil(
				PTAbstractTest.injector);
		PTBusinessTestUtil business = new PTBusinessTestUtil(
				PTAbstractTest.injector);
		PTCustomerTestUtil customer = new PTCustomerTestUtil(
				PTAbstractTest.injector);
		PTSupplierTestUtil supplier = new PTSupplierTestUtil(
				PTAbstractTest.injector);
		PTProductTestUtil product = new PTProductTestUtil(
				PTAbstractTest.injector);
		PTInvoiceTestUtil invoice = new PTInvoiceTestUtil(
				PTAbstractTest.injector);
		PTCreditNoteTestUtil creditNote = new PTCreditNoteTestUtil(
				PTAbstractTest.injector);
		PTSimpleInvoiceTestUtil simpleInvoice = new PTSimpleInvoiceTestUtil(
				PTAbstractTest.injector);

		DAOPTRegionContext daoPTRegionContext = PTAbstractTest.injector
				.getInstance(DAOPTRegionContext.class);
		PTRegionContextEntity myContext = (PTRegionContextEntity) daoPTRegionContext
				.get(c.getUID(Config.Key.Context.Portugal.UUID));

		/* ADDRESSES */
		PTAddress.Builder addressBuilder1 = address.getAddressBuilder(
				"Av. Republica", "Nº 3 - 3º Esq.",
				"Av. Republica Nº 3 - 3º Esq.", "", "Lisboa", "1700-232", "",
				"PT");
		PTAddress.Builder addressBuilder2 = address.getAddressBuilder(
				"Av. Liberdade", "Nº 4 - 5º Dir.",
				"Av. Liberdade, Nº 4 - 5º Dir.", "", "Lisboa", "1500-123", "",
				"PT");
		PTAddress.Builder addressBuilder3 = address.getAddressBuilder(
				"Campo Grande", "Condomínio X", "Lote 20, Andar 3", "",
				"Lisboa", "1000-253", "", "PT");

		/* CONTACTS */
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
		PTApplication.Builder applicationBuilder = application
				.getApplicationBuilder("APP", "1.0", "My Business",
						"523456789", "hhtp://www.app.mybusiness.web", 1,
						"http://here", contactBuilder);
		PTApplicationEntity applicationEntity = (PTApplicationEntity) applicationBuilder
				.build();

		/* BUSINESS */
		DAOPTBusiness daoPTBusiness = PTAbstractTest.injector
				.getInstance(DAOPTBusiness.class);
		PTBusinessEntity businessEntity = business.getBusinessEntity();

		/* CUSTOMERS */
		DAOPTCustomer daoPTCustomer = PTAbstractTest.injector
				.getInstance(DAOPTCustomer.class);
		PTCustomerEntity customerEntity = customer.getCustomerEntity("Zé",
				"26949843873", false, addressBuilder2, contactBuilder2);
		daoPTCustomer.create(customerEntity);

		PTCustomerEntity genericCustomerEntity = (PTCustomerEntity) daoPTCustomer
				.get(c.getUID(Config.Key.Customer.Generic.UUID));

		/* SUPPLIERS */
		DAOPTSupplier daoPTSupplier = PTAbstractTest.injector
				.getInstance(DAOPTSupplier.class);

		PTSupplierEntity supplierEntity = supplier.getSupplierEntity(
				"YourSupplier", "5324532453", false, addressBuilder3,
				contactBuilder3);
		daoPTSupplier.create(supplierEntity);

		/* TAXES */
		DAOPTTax daoPTTax = PTAbstractTest.injector.getInstance(DAOPTTax.class);

		/* PRODUCTS */
		DAOPTProduct daoPTProduct = PTAbstractTest.injector
				.getInstance(DAOPTProduct.class);

		PTProductEntity productEntity1 = product.getProductEntity("124233465",
				"Kg", "34254567", "Produto", ProductType.GOODS);
		PTProductEntity productEntity2 = product.getProductEntity("243532453",
				"hh:mm:ss", "13423534", "Estacionamento", ProductType.SERVICE);

		// INVOICES
		DAOPTInvoice daoPTInvoice = PTAbstractTest.injector
				.getInstance(DAOPTInvoice.class);

		KeyGenerator keyGenerator = new KeyGenerator(
				SAFTExportTest.PRIVATE_KEY_DIR);
		PrivateKey privateKey = keyGenerator.getPrivateKey();
		PublicKey publicKey = keyGenerator.getPublicKey();
		String prevHash = null;

		List<PTInvoiceEntity> invoices = new ArrayList<PTInvoiceEntity>();
		for (int i = 1; i < SAFTExportTest.MAX_INVOICES; i++) {
			PTInvoiceEntity invoiceEntity = invoice.getInvoiceEntity();
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
		DAOPTSimpleInvoice daoPTSimpleInvoice = PTAbstractTest.injector
				.getInstance(DAOPTSimpleInvoice.class);
		PTSimpleInvoiceEntity simpleInvoiceEntity = simpleInvoice
				.getSimpleInvoiceEntity();
		simpleInvoiceEntity.setHash(GenerateHash.generateHash(privateKey,
				publicKey, simpleInvoiceEntity.getDate(),
				simpleInvoiceEntity.getCreateTimestamp(),
				simpleInvoiceEntity.getNumber(),
				simpleInvoiceEntity.getAmountWithTax(), null));
		daoPTSimpleInvoice.create(simpleInvoiceEntity);

		// CREDIT NOTE
		DAOPTCreditNote daoPTCreditNote = PTAbstractTest.injector
				.getInstance(DAOPTCreditNote.class);
		PTCreditNoteEntity creditNoteEntity = creditNote.getCreditNoteEntity(
				TYPE.NC, this.getNewIssuedInvoice());
		creditNoteEntity.setHash(GenerateHash.generateHash(privateKey,
				publicKey, creditNoteEntity.getDate(),
				creditNoteEntity.getCreateTimestamp(),
				creditNoteEntity.getNumber(),
				creditNoteEntity.getAmountWithTax(), null));
		daoPTCreditNote.create(creditNoteEntity);

		this.exportSAFT(daoPTRegionContext, applicationEntity, businessEntity,
				daoPTCustomer, daoPTSupplier, daoPTTax, daoPTProduct,
				daoPTInvoice, daoPTSimpleInvoice, daoPTCreditNote);
	}

	private void exportSAFT(DAOPTRegionContext daoPTRegionContext,
			PTApplicationEntity applicationEntity,
			PTBusinessEntity businessEntity, DAOPTCustomer daoPTCustomer,
			DAOPTSupplier daoPTSupplier, DAOPTTax daoPTTax,
			DAOPTProduct daoPTProduct, DAOPTInvoice daoPTInvoice,
			DAOPTSimpleInvoice daoPTSimpleInvoice,
			DAOPTCreditNote daoPTCreditNote) throws FileNotFoundException,
		SAFTPTExportException {

		PTSAFTFileGenerator generator = new PTSAFTFileGenerator();

		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 1, 1);

		PrintStream stream = new PrintStream(SAFTExportTest.SAFT_OUTPUT
				+ "SAFT.xml");
		generator.generateSAFTFile(stream, businessEntity, applicationEntity,
				"1234", calendar.getTime(), new Date(), daoPTCustomer,
				daoPTSupplier, daoPTProduct, daoPTTax, daoPTRegionContext,
				daoPTInvoice, daoPTSimpleInvoice, daoPTCreditNote);
	}
}
