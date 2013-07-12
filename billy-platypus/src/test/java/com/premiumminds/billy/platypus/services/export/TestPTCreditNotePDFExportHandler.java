/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
 *
 * billy is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.platypus.services.export;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import com.premiumminds.billy.core.persistence.entities.TaxEntity.Unit;
import com.premiumminds.billy.core.persistence.entities.jpa.AddressEntityImpl;
import com.premiumminds.billy.core.persistence.entities.jpa.CustomerEntity;
import com.premiumminds.billy.core.persistence.entities.jpa.ProductEntity;
import com.premiumminds.billy.core.util.ProductType;
import com.premiumminds.billy.core.util.TaxType;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.DocumentState;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity.PaymentMechanism;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTFinancialDocumentEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.PTTaxEntity;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNotePDFExportHandler;
import com.premiumminds.billy.portugal.services.export.pdf.creditnote.PTCreditNoteTemplateBundle;

public class TestPTCreditNotePDFExportHandler {
	public static final int NUM_ENTRIES = 1; 
	public static final String XSL_PATH = "src/main/resources/pt_creditnote.xsl";
	public static final String PDF_PATH = "src/main/resources/CreditNote.pdf";
	public static final String LOGO_PATH = "src/main/resources/logoBig.png";

	public static final String BUSINESS_EMAIL = "testmail@premium-minds.com";
	public static final String BUSINESS_PHONE = "21 123 1234";
	public static final String BUSINESS_FAX = "21 321 4321";
	public static final String CUSTOMER_NUMBER = "123456";
	public static final String SOFTWARE_CERTIFICATE_NUMBER = "4321";
	public static final byte[] SAMPLE_HASH = {0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf
		,0xa, 0x1, 0x3, 0xf, 0x7, 0x5, 0x4, 0xd, 0xa, 0x1, 0x3, 0xf};
	@Test
	public void testPDFcreation() throws NoSuchAlgorithmException, ExportServiceException {
		PTCreditNoteTemplateBundle bundle = 
				new PTCreditNoteTemplateBundle(LOGO_PATH, getClass().getResourceAsStream(XSL_PATH), PDF_PATH, BUSINESS_EMAIL, BUSINESS_PHONE, BUSINESS_FAX, SOFTWARE_CERTIFICATE_NUMBER);
		PTCreditNotePDFExportHandler handler = new PTCreditNotePDFExportHandler();
		/*File file = */handler.toFile(generatePTCreditNote(), bundle);
		//		MessageDigest md = MessageDigest.getInstance("MD5");
		//		byte result[], buf[] =new byte[2048];
		//		InputStream is = new FileInputStream(file);
		//		try {
		//		while(is.read(buf)> 0) {
		//			md.update(buf);
		//		}
		//		
		//		result = md.digest();
		//		System.out.println(Base64.encodeBase64String(result));
		//		}finally {
		//			is.close();
		//		}
	}

	private PTInvoiceEntity generatePTInvoiceEntity() {
		ProductEntity product = new ProductEntity(ProductType.SERVICE, "A123Z","Estacionamentos","Estacionamento Lisboa Verde", "1234", null);
		List<PTTaxEntity> entryTax = new ArrayList<PTTaxEntity>();
		entryTax.add(new PTTaxEntity(null, "cenas", TaxType.VAT, BigDecimal.TEN, Unit.PERCENTAGE, null, null, null, null));
		List<PTFinancialDocumentEntryEntity> entries =  new ArrayList<PTFinancialDocumentEntryEntity>();
		for(int i = 1; i < NUM_ENTRIES + 1; ++i) {
			entries.add(new PTFinancialDocumentEntryEntity(
					i
					, product
					, "cenas"
					, null
					, BigDecimal.ONE
					, BigDecimal.TEN
					, BigDecimal.TEN
					, BigDecimal.ONE
					, BigDecimal.ZERO
					, BigDecimal.TEN.add(BigDecimal.ONE)
					, null
					, entryTax
					, null
					, null
					, null
					, null));
		}
		AddressEntityImpl cAddr = new AddressEntityImpl("Portugal"
				, "Av. Marquês de Tomar nº1"
				, "1º Andar"
				, "2º Lote Esquerdo"
				, "Lisboa"
				, "Lisboa"
				, "1234-123");
		AddressEntityImpl bAddr = new AddressEntityImpl("Portugal"
				, "Rua das Cenas nº 7"
				, "2º Direito"
				, null
				, null
				, "Lisboa"
				, "1234-567");
		PTBusinessEntity business = new PTBusinessEntity(null
				, "999222999"
				, "Estacionamentos S.A"
				, "Estacionamentos S.A Comercial"
				, bAddr
				, null
				, null
				, null
				, null
				, null
				, null
				, null);

		CustomerEntity customer = new CustomerEntity("António Joaquim da Costa Pascoal"
				, null
				, "9999999990"
				, cAddr
				, cAddr
				, cAddr
				, null
				, null
				, false);
		PTInvoiceEntity invoice = new PTInvoiceEntity(
				business
				, null
				, customer
				, entries
				, entryTax
				, DateTime.now().toDate()
				, "Pronto Pagamento"
				, null
				, BigDecimal.TEN
				, BigDecimal.ONE
				, BigDecimal.ZERO
				, BigDecimal.TEN.add(BigDecimal.ONE)
				, null
				, false
				, SAMPLE_HASH
				, "A-101"
				, 100001L
				, DocumentState.NORMAL
				, PaymentMechanism.CASH
				, null
				, null
				,null
				,null
				,null
				,null
				,null);

		return invoice;
	}
	
	
	private PTCreditNoteEntity generatePTCreditNote() {
		PTInvoiceEntity referenceInvoice = generatePTInvoiceEntity();
		ProductEntity product = new ProductEntity(ProductType.SERVICE, "A123Z","Estacionamentos","Estacionamento Lisboa Verde", "1234", null);
		List<PTTaxEntity> entryTax = new ArrayList<PTTaxEntity>();
		entryTax.add(new PTTaxEntity(null, "cenas", TaxType.VAT, BigDecimal.TEN, Unit.PERCENTAGE, null, null, null, null));
		List<PTFinancialDocumentEntryEntity> entries =  new ArrayList<PTFinancialDocumentEntryEntity>();
		for(int i = 1; i < NUM_ENTRIES + 1; ++i) {
			entries.add(new PTFinancialDocumentEntryEntity(
					i
					, product
					, "cenas"
					, null
					, BigDecimal.ONE
					, BigDecimal.TEN
					, BigDecimal.TEN
					, BigDecimal.ONE
					, BigDecimal.ZERO
					, BigDecimal.TEN.add(BigDecimal.ONE)
					, null
					, entryTax
					, referenceInvoice
					, null
					, null
					, null));
		}
		AddressEntityImpl cAddr = new AddressEntityImpl("Portugal"
				, "Av. Marquês de Tomar nº1"
				, "1º Andar"
				, "2º Lote Esquerdo"
				, "Lisboa"
				, "Lisboa"
				, "1234-123");
		AddressEntityImpl bAddr = new AddressEntityImpl("Portugal"
				, "Rua das Cenas nº 7"
				, "2º Direito"
				, null
				, null
				, "Lisboa"
				, "1234-567");
		PTBusinessEntity business = new PTBusinessEntity(null
				, "999222999"
				, "Estacionamentos S.A"
				, "Estacionamentos S.A Comercial"
				, bAddr
				, null
				, null
				, null
				, null
				, null
				, null
				, null);

		CustomerEntity customer = new CustomerEntity("António Joaquim da Costa Pascoal"
				, null
				, "9999999990"
				, cAddr
				, cAddr
				, cAddr
				, null
				, null
				, false);
		PTCreditNoteEntity creditNote = new PTCreditNoteEntity(
				business
				, null
				, customer
				, entries
				, entryTax
				, DateTime.now().toDate()
				, "Pronto Pagamento"
				, null
				, BigDecimal.TEN
				, BigDecimal.ONE
				, BigDecimal.ZERO
				, BigDecimal.TEN.add(BigDecimal.ONE)
				, Currency.getInstance("EUR")
				, false
				, SAMPLE_HASH
				, "A-101"
				, 100001L
				, DocumentState.NORMAL
				, PaymentMechanism.CASH
				, null
				, null
				, null
				, null
				, null
				, null
				, null
				, "cheiro mal dos pés");

		return creditNote;
	}

}
