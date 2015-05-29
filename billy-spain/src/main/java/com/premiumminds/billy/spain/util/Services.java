/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy spain (ES Pack).
 *
 * billy spain (ES Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy spain (ES Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy spain (ES Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.spain.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.documents.IssuingParams;
import com.premiumminds.billy.core.services.documents.impl.DocumentIssuingServiceImpl;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.spain.persistence.entities.ESCreditNoteEntity;
import com.premiumminds.billy.spain.persistence.entities.ESCreditReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.ESInvoiceEntity;
import com.premiumminds.billy.spain.persistence.entities.ESReceiptEntity;
import com.premiumminds.billy.spain.persistence.entities.ESSimpleInvoiceEntity;
import com.premiumminds.billy.spain.services.documents.ESCreditNoteIssuingHandler;
import com.premiumminds.billy.spain.services.documents.ESCreditReceiptIssuingHandler;
import com.premiumminds.billy.spain.services.documents.ESInvoiceIssuingHandler;
import com.premiumminds.billy.spain.services.documents.ESReceiptIssuingHandler;
import com.premiumminds.billy.spain.services.documents.ESSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.spain.services.documents.util.ESIssuingParams;
import com.premiumminds.billy.spain.services.entities.ESGenericInvoice;

public class Services {

	private final Injector injector;
	private DocumentIssuingService issuingService;
	private PersistenceServices persistenceService;

	public Services(Injector injector) {
		this.injector = injector;
		this.issuingService = injector.getInstance(DocumentIssuingServiceImpl.class);
		this.persistenceService = new PersistenceServices(injector);
		this.setupServices();
	}

	private void setupServices() {
		this.issuingService.addHandler(ESInvoiceEntity.class,
				this.injector.getInstance(ESInvoiceIssuingHandler.class));
		this.issuingService.addHandler(ESCreditNoteEntity.class,
				this.injector.getInstance(ESCreditNoteIssuingHandler.class));
		this.issuingService.addHandler(ESSimpleInvoiceEntity.class,
				this.injector.getInstance(ESSimpleInvoiceIssuingHandler.class));
		this.issuingService.addHandler(ESReceiptEntity.class, 
				this.injector.getInstance(ESReceiptIssuingHandler.class));
		this.issuingService.addHandler(ESCreditReceiptEntity.class
				, this.injector.getInstance(ESCreditReceiptIssuingHandler.class));
	}

	/**
	 * @return {@link PersistenceServices}
	 */
	public PersistenceServices entities() {
		return this.persistenceService;
	}

	/**
	 * Issue a new document and store it in the database.
	 * 
	 * @param {@link Builder} of the document to issue.
	 * @param {@link IssuingParams} required to issue the document.
	 * @return The newly issued document
	 * @throws DocumentIssuingException
	 */
	public <T extends ESGenericInvoice> T issueDocument(Builder<T> builder,
			ESIssuingParams issuingParameters) throws DocumentIssuingException {
		return this.issuingService.issue(builder, issuingParameters);
	}

	public <T extends ESGenericInvoice> T issueDocument(Builder<T> builder,
			ESIssuingParams issuingParameters, String ticketUID)
			throws DocumentIssuingException {
		return this.issuingService.issue(builder, issuingParameters, ticketUID);
	}

}
