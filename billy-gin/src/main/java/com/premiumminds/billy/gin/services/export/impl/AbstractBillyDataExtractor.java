/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy GIN.
 *
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services.export.impl;

import com.premiumminds.billy.gin.services.export.Exemption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.premiumminds.billy.core.services.entities.Address;
import com.premiumminds.billy.core.services.entities.Business;
import com.premiumminds.billy.core.services.entities.Contact;
import com.premiumminds.billy.core.services.entities.Customer;
import com.premiumminds.billy.core.services.entities.Payment;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.gin.services.export.AddressData;
import com.premiumminds.billy.gin.services.export.BusinessData;
import com.premiumminds.billy.gin.services.export.ContactData;
import com.premiumminds.billy.gin.services.export.CostumerData;
import com.premiumminds.billy.gin.services.export.InvoiceEntryData;
import com.premiumminds.billy.gin.services.export.PaymentData;
import com.premiumminds.billy.gin.services.export.ProductData;
import com.premiumminds.billy.gin.services.export.TaxData;

public abstract class AbstractBillyDataExtractor {

    protected CostumerData extractCostumer(Customer costumerEntity) {
        Address costumerAddressEntity = costumerEntity.getBillingAddress();
        AddressData costumerBillingAddress = new AddressData(costumerAddressEntity.getISOCountry(),
                costumerAddressEntity.getDetails(), costumerAddressEntity.getCity(), costumerAddressEntity.getRegion(),
                costumerAddressEntity.getPostalCode());
        return new CostumerData(costumerEntity.getUID(), costumerEntity.getTaxRegistrationNumber(),
                costumerEntity.getName(), costumerBillingAddress);
    }

    protected BusinessData extractBusiness(Business businessEntity) {
        Address businessAddressEntity = businessEntity.getAddress();
        AddressData businessAddress = new AddressData(businessAddressEntity.getISOCountry(),
                businessAddressEntity.getDetails(), businessAddressEntity.getCity(), businessAddressEntity.getRegion(),
                businessAddressEntity.getPostalCode());
        Contact contactEntity = businessEntity.getMainContact();
        ContactData businessContact =
                new ContactData(contactEntity.getTelephone(), contactEntity.getFax(), contactEntity.getEmail());

        return new BusinessData(businessEntity.getName(), businessEntity.getFinancialID(), businessAddress,
                businessContact);
    }

    protected List<InvoiceEntryData> extractEntries(List<? extends GenericInvoiceEntry> entryEntities) {
        List<InvoiceEntryData> entries = new ArrayList<>(entryEntities.size());
        for (GenericInvoiceEntry entry : entryEntities) {
            ProductData product =
                    new ProductData(entry.getProduct().getProductCode(), entry.getProduct().getDescription());

            List<TaxData> taxes = this.extractTaxes(entry.getTaxes());

            entries.add(new InvoiceEntryData(
                product, entry.getDescription(), entry.getQuantity(), entry.getTaxAmount(), entry.getUnitAmountWithTax(),
                entry.getAmountWithTax(), entry.getAmountWithoutTax(), taxes, entry.getUnitOfMeasure(),
                Exemption.setExemption(entry.getTaxExemptionCode(), entry.getTaxExemptionReason())));
        }

        return entries;
    }

    protected List<TaxData> extractTaxes(Collection<Tax> taxEntities) {
        List<TaxData> taxes = new ArrayList<>(taxEntities.size());
        for (Tax tax : taxEntities) {
            taxes.add(new TaxData(tax.getUID(), tax.getValue(), tax.getTaxRateType(), tax.getDescription(),
                    tax.getDesignation()));
        }

        return taxes;
    }

    protected List<PaymentData> extractPayments(List<? extends Payment> paymentEntities) {
        List<PaymentData> payments = new ArrayList<>(paymentEntities.size());
        for (Payment payment : paymentEntities) {
            payments.add(new PaymentData(payment.getPaymentMethod()));
        }

        return payments;
    }

}
