/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy.
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.builders.impl;

import javax.xml.bind.ValidationException;

import com.premiumminds.billy.core.util.DocumentType;
import com.premiumminds.billy.portugal.services.builders.PTCreditNoteBuilder;
import com.premiumminds.billy.portugal.services.entities.PTCreditNote;
import com.premiumminds.billy.portugal.services.entities.PTFinancialDocumentEntry;
import com.premiumminds.billy.portugal.services.entities.impl.PTCreditNoteImpl;

public class PTCreditNoteBuilderImpl<TBuilder extends PTCreditNoteBuilderImpl<TBuilder, TEntry, TDocument>, TEntry extends PTFinancialDocumentEntry, TDocument extends PTCreditNote>
extends PTFinancialDocumentBuilderImpl<TBuilder, TEntry, TDocument>
implements PTCreditNoteBuilder<TBuilder, TEntry, TDocument> {

	public PTCreditNoteBuilderImpl() {
		this.document = new PTCreditNoteImpl();
	}

	@Override
	protected PTCreditNoteImpl getDocumentImpl() {
		return (PTCreditNoteImpl) this.document;
	}

	@Override
	public TBuilder setReasonForCredit(String reasonForCredit) {
		getDocumentImpl().setReasonForCredit(reasonForCredit);
		return getBuilder();
	}
	
	@Override
	public TBuilder setType(DocumentType type) {
		if(DocumentType.CREDIT_NOTE != type) {
			System.err.println("Tried to set credit note type to " + type.name() + ". Ignoring.");
		}
		return super.setType(type);
	}
	
	@Override
	public TDocument build() throws ValidationException {
		setType(DocumentType.CREDIT_NOTE);
		return super.build();
	}

}
