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
package com.premiumminds.billy.portugal.util;

import com.google.inject.Injector;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.documents.DocumentIssuingService;
import com.premiumminds.billy.core.services.exceptions.DocumentIssuingException;
import com.premiumminds.billy.portugal.persistence.entities.PTCreditNoteEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTSimpleInvoiceEntity;
import com.premiumminds.billy.portugal.services.documents.PTCreditNoteIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.PTInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.PTSimpleInvoiceIssuingHandler;
import com.premiumminds.billy.portugal.services.documents.util.PTIssuingParams;
import com.premiumminds.billy.portugal.services.entities.PTInvoice;

public class Services {

	private final Injector injector;
	private DocumentIssuingService issuingService;
	private Persistence persistenceService;

	public Services(Injector injector) {
		this.injector = injector;
		this.issuingService = injector
				.getInstance(DocumentIssuingService.class);
		this.persistenceService = new Persistence(injector);
		this.setupServices();
	}

	private void setupServices() {
		this.issuingService.addHandler(PTInvoiceEntity.class,
				this.injector.getInstance(PTInvoiceIssuingHandler.class));
		this.issuingService.addHandler(PTCreditNoteEntity.class,
				this.injector.getInstance(PTCreditNoteIssuingHandler.class));
		this.issuingService.addHandler(PTSimpleInvoiceEntity.class,
				this.injector.getInstance(PTSimpleInvoiceIssuingHandler.class));
	}

	public Persistence persistence() {
		return persistenceService;
	}

	public <T extends PTInvoice> T issueDocument(Builder<T> builder,
			PTIssuingParams issuingParameters) throws DocumentIssuingException {
		return this.issuingService.issue(builder, issuingParameters);
	}

}
