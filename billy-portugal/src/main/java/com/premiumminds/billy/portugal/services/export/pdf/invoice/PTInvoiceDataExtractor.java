/**
 * Copyright (C) 2017 Premium Minds.
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
///**
// * Copyright (C) 2017 Premium Minds.
// *
// * This file is part of billy portugal (PT Pack).
// *
// * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
// * the terms of the GNU Lesser General Public License as published by the Free
// * Software Foundation, either version 3 of the License, or (at your option) any
// * later version.
// *
// * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
// * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
// * details.
// *
// * You should have received a copy of the GNU Lesser General Public License
// * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
// */
//package com.premiumminds.billy.portugal.services.export.pdf.invoice;
//
//import javax.inject.Inject;
//
//import org.apache.commons.codec.binary.Base64;
//
//import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
//import com.premiumminds.billy.core.services.UID;
//import com.premiumminds.billy.core.util.BillyMathContext;
//import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
//import com.premiumminds.billy.gin.services.export.BillyTransformerData;
//import com.premiumminds.billy.gin.services.export.BillyDataExtractorBundle;
//import com.premiumminds.billy.gin.services.export.ParamsTree;
//import com.premiumminds.billy.gin.services.impl.pdf.AbstractDataExtractor;
//import com.premiumminds.billy.portugal.Config;
//import com.premiumminds.billy.portugal.persistence.dao.DAOPTInvoice;
//import com.premiumminds.billy.portugal.persistence.entities.PTInvoiceEntity;
//import com.premiumminds.billy.portugal.services.export.pdf.PTDataExtractorBundle;
//
//public class PTInvoiceDataExtractor extends AbstractDataExtractor {
//	
//	protected static class PTParamKeys {
//		public static final String	INVOICE_HASH				= "hash";
//		public static final String	SOFTWARE_CERTIFICATE_NUMBER	= "certificateNumber";
//		public static final String	INVOICE_PAYSETTLEMENT		= "paymentSettlement";
//	}
//	
//	private final DAOPTInvoice daoInvoice;
//	private final Config config;
//
//	@Inject
//	public PTInvoiceDataExtractor(DAOPTInvoice daoInvoice, Config config) {
//		super(BillyMathContext.get());
//		this.daoInvoice = daoInvoice;
//		this.config = config;
//	}
//	
//	@Override
//	public BillyTransformerData extract(final UID uidDoc, final BillyDataExtractorBundle extractorBundle) throws ExportServiceException {
//		final ParamsTree<String, String> params = doExtract(uidDoc, extractorBundle);
//		return new BillyDataExtractorImpl(params);
//	}
//	
//	private ParamsTree<String, String> doExtract(UID uidDoc, BillyDataExtractorBundle extractorBundle) throws ExportServiceException {
//		
//		if (!(extractorBundle instanceof PTDataExtractorBundle)) {
//			throw new ExportServiceException("Cannot handle bundle of type "
//					+ extractorBundle.getClass().getCanonicalName());
//		}
//		PTDataExtractorBundle bundle = (PTDataExtractorBundle) extractorBundle;
//		PTInvoiceEntity invoice = (PTInvoiceEntity) this.daoInvoice.get(uidDoc);
//		
//		ParamKeys.ROOT = "invoice";
//		ParamsTree<String, String> params = super.mapDocumentToParamsTree(
//				invoice, bundle);
//
//		params.getRoot().addChild(PTParamKeys.INVOICE_HASH,
//				this.getVerificationHashString(invoice.getHash().getBytes()));
//
//		params.getRoot().addChild(PTParamKeys.SOFTWARE_CERTIFICATE_NUMBER,
//				bundle.getSoftwareCertificationId());
//		
//		return params;
//	}
//	
//	private String getVerificationHashString(byte[] hash) {
//		String hashString = Base64.encodeBase64String(hash);
//		String rval = hashString.substring(0, 1) + hashString.substring(10, 11)
//				+ hashString.substring(20, 21) + hashString.substring(30, 31);
//
//		return rval;
//	}
//
//	@Override
//	protected <T extends BillyDataExtractorBundle, K extends GenericInvoiceEntity> String getCustomerFinancialId(K invoice,
//			T bundle) {
//		
//		PTDataExtractorBundle template = (PTDataExtractorBundle) bundle;
//		return (invoice.getCustomer().getUID()
//				.equals(this.config.getUUID(Config.Key.Customer.Generic.UUID)) ? 
//						template.getGenericCustomer() : invoice.getCustomer().getTaxRegistrationNumber());
//	}
//
//}