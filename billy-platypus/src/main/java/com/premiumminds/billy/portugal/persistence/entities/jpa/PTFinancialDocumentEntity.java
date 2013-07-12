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
package com.premiumminds.billy.portugal.persistence.entities.jpa;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.premiumminds.billy.core.persistence.entities.AddressEntity;
import com.premiumminds.billy.core.util.DocumentType;
import com.premiumminds.billy.portugal.Config;
import com.premiumminds.billy.portugal.persistence.entities.IPTBusinessEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTFinancialDocumentEntryEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.IPTTaxEntity;
import com.premiumminds.billy.portugal.services.util.Documents;

@Entity
@Table(name = Config.TABLE_PREFIX + "FINANCIAL_DOCUMENT")
public class PTFinancialDocumentEntity extends FinancialDocumentEntity
		implements IPTFinancialDocumentEntity {
	private static final long serialVersionUID = 1L;

	@Column(name="HASH", insertable=true, updatable=false, nullable=false)
	private byte[] hash;
	
	/* FIXME confirm this */
	@Column(name="DOCUMENT_NUMBER_SAFT_PT", insertable=true, updatable=false, nullable=false)
	private String documentNumberSAFTPT;
	
	@Column(name="SEQUENCE_ID", insertable=true, updatable=false, nullable=false)
	private String sequenceID;
	
	@Column(name="SEQUENCIAL_NUMBER", insertable=true, updatable=false, nullable=false)
	private long sequencialNumber;

	@Enumerated(EnumType.STRING) 
	@Column(name="STATE", insertable=true, updatable=false, nullable=false)
	private DocumentState state;
	
	@Enumerated(EnumType.STRING)
	@Column(name="PAYMENT_MECHANISM", insertable=true, updatable=false, nullable=false)
	private PaymentMechanism paymentMechanism;
	
	@Column(name="DELIVERY_ORIGIN_ID", insertable=true, updatable=false, nullable=true)
	private String deliveryOriginId;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ID_DELIVERY_ORIGIN_ADDRESS", referencedColumnName = "ID")
	private AddressEntity deliveryOriginAddress;

	@Temporal(TemporalType.DATE)
	@Column(name="DELIVERY_SHIPPING_DATE", insertable=true, updatable=false, nullable=true)
	private Date deliveryShippingDate;

	@Column(name="DELIVERY_ID", insertable=true, updatable=false, nullable=true)
	private String deliveryId;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ID_DELIVERY_DESTINATION_ADDRESS", referencedColumnName = "ID")
	private AddressEntity deliveryDestinationAddress;

	@Temporal(TemporalType.DATE)
	@Column(name="DELIVERY_DATE", insertable=true, updatable=false, nullable=true)
	private Date deliveryDestinationDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ID_PT_REGION_CONTEXT", referencedColumnName = "ID")
	private PTRegionContextEntity ptRegionContext;

	
	public PTFinancialDocumentEntity(){}
	
	public PTFinancialDocumentEntity(
			PTBusinessEntity business, 
			BusinessOfficeEntity businessOffice,
			CustomerEntity customer,
			DocumentType type,
			List<PTFinancialDocumentEntryEntity> documentEntries,
			List<PTTaxEntity> documentTaxes, 
			Date issueDate,
			String settlementDescription,
			Date settlementDate,
			BigDecimal netTotal,
			BigDecimal taxTotal,
			BigDecimal settlementTotal,
			BigDecimal grossTotal,
			Currency currency,
			boolean selfBilling,
			byte[] hash,
			String sequenceId,
			long sequencialNumber,
			DocumentState state,
			PaymentMechanism paymentMechanism,
			String deliveryOriginId,
			AddressEntity deliveryOriginAddress,
			Date deliveryShippingDate,
			String deliveryId,
			AddressEntity deliveryDestinationAddress,
			Date deliveryDate,
			PTRegionContextEntity ptRegionContextEntity){
		
		super(business, businessOffice, customer, type, documentEntries, documentTaxes, issueDate,
				settlementDescription, settlementDate, netTotal, taxTotal, settlementTotal, 
				grossTotal, currency, selfBilling);
		
		setHash(hash);
		setSequenceID(sequenceId);
		setSequencialNumber(sequencialNumber);
		setDocumentState(state);
		setPaymentMechanism(paymentMechanism);
		setDeliveryOriginId(deliveryOriginId);
		setDeliveryOriginAddress(deliveryOriginAddress);
		setDeliveryShippingDate(deliveryShippingDate);
		setDeliveryId(deliveryId);
		setDeliveryDestinationAddress(deliveryDestinationAddress);
		setDeliveryDestinationDate(deliveryDate);
		setPTRegionContext(ptRegionContextEntity);
	}

	
	//GETTERS
	
	@Override
	public String getDocumentNumberSAFTPT() {
		return Documents.getDocumentNo(this);
	}

	@Override
	public String getSequenceID() {
		return sequenceID;
	}

	@Override
	public long getSequencialNumber() {
		return sequencialNumber;
	}

	@Override
	public DocumentState getDocumentState() {
		return state;
	}

	@Override
	public byte[] getHash() {
		return hash;
	}

	@Override
	public PaymentMechanism getPaymentMechanism() {
		return paymentMechanism;
	}
	
	@Override
	public String getDeliveryOriginId() {
		return deliveryOriginId;
	}

	@Override
	public AddressEntity getDeliveryOriginAddress() {
		return deliveryOriginAddress;
	}

	@Override
	public Date getDeliveryShippingDate() {
		return deliveryShippingDate;
	}

	@Override
	public String getDeliveryId() {
		return deliveryId;
	}

	@Override
	public AddressEntity getDeliveryDestinationAddress() {
		return deliveryDestinationAddress;
	}

	@Override
	public Date getDeliveryDate() {
		return deliveryDestinationDate;
	}
	
	@Override
	public IPTBusinessEntity getBusiness() {
		return super.getBusiness();
	}
	
	@Override
	public List<IPTFinancialDocumentEntryEntity> getDocumentEntries() {
		return (List<IPTFinancialDocumentEntryEntity>) super.getDocumentEntries();
	}
	
	@Override
	public List<IPTTaxEntity> getDocumentTaxes() {
		return (List<IPTTaxEntity>) super.getDocumentTaxes();
	}
	
	@Override
	public IPTRegionContextEntity getPTRegionContext() {
		return ptRegionContext;
	}
	
	
	//SETTERS

	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	
	private void setSequenceID(String sequenceID) {
		this.sequenceID = sequenceID;
		this.documentNumberSAFTPT = Documents.getDocumentNo(this);
	}

	@Override
	public void setSequencialNumber(long sequentialNumber) {
		this.sequencialNumber = sequentialNumber;
		this.documentNumberSAFTPT = Documents.getDocumentNo(this);
	}

	@Override
	public void setDocumentState(DocumentState state) {
		this.state = state;
	}

	private void setPaymentMechanism(PaymentMechanism paymentMechanism) {
		this.paymentMechanism = paymentMechanism;
	}
	
	public void setDeliveryOriginId(String deliveryOriginId) {
		this.deliveryOriginId = deliveryOriginId;
	}

	public void setDeliveryOriginAddress(AddressEntity deliveryOriginAddress) {
		this.deliveryOriginAddress = deliveryOriginAddress;
	}

	public void setDeliveryShippingDate(Date deliveryShippingDate) {
		this.deliveryShippingDate = deliveryShippingDate;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public void setDeliveryDestinationAddress(
			AddressEntity deliveryDestinationAddress) {
		this.deliveryDestinationAddress = deliveryDestinationAddress;
	}

	public void setDeliveryDestinationDate(Date deliveryDestinationDate) {
		this.deliveryDestinationDate = deliveryDestinationDate;
	}
	
	@Override
	public void setPTRegionContext(IPTRegionContextEntity ptRegionContext) {
		this.ptRegionContext = (PTRegionContextEntity) ptRegionContext;
	}
	
}
