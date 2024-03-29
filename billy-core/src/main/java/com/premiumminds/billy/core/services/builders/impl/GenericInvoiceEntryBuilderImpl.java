/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy core.
 *
 * billy core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy core is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.core.services.builders.impl;

import com.premiumminds.billy.core.ExternalID;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;

import com.premiumminds.billy.core.exceptions.BillyValidationException;
import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoice;
import com.premiumminds.billy.core.persistence.dao.AbstractDAOGenericInvoiceEntry;
import com.premiumminds.billy.core.persistence.dao.DAOContext;
import com.premiumminds.billy.core.persistence.dao.DAOProduct;
import com.premiumminds.billy.core.persistence.dao.DAOTax;
import com.premiumminds.billy.core.persistence.entities.ContextEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntity;
import com.premiumminds.billy.core.persistence.entities.GenericInvoiceEntryEntity;
import com.premiumminds.billy.core.persistence.entities.ProductEntity;
import com.premiumminds.billy.core.services.Builder;
import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder;
import com.premiumminds.billy.core.services.entities.Context;
import com.premiumminds.billy.core.services.entities.Product;
import com.premiumminds.billy.core.services.entities.ShippingPoint;
import com.premiumminds.billy.core.services.entities.Tax;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoice;
import com.premiumminds.billy.core.services.entities.documents.GenericInvoiceEntry;
import com.premiumminds.billy.core.util.BillyMathContext;
import com.premiumminds.billy.core.util.BillyValidator;
import com.premiumminds.billy.core.util.DiscountType;
import com.premiumminds.billy.core.util.Localizer;
import com.premiumminds.billy.core.util.NotImplemented;
import com.premiumminds.billy.core.util.NotOnUpdate;

public class GenericInvoiceEntryBuilderImpl<TBuilder extends GenericInvoiceEntryBuilderImpl<TBuilder, TEntry,
        TInvoice, TDAOEntry, TDAOInvoice>, TEntry extends GenericInvoiceEntry, TInvoice extends GenericInvoice,
        TDAOEntry extends AbstractDAOGenericInvoiceEntry<?>, TDAOInvoice extends AbstractDAOGenericInvoice<?>>
        extends AbstractBuilder<TBuilder, TEntry> implements GenericInvoiceEntryBuilder<TBuilder, TEntry, TInvoice> {

    protected static final Localizer LOCALIZER = new Localizer("com/premiumminds/billy/core/i18n/FieldNames");

    protected TDAOEntry daoEntry;
    protected TDAOInvoice daoInvoice;
    protected DAOTax daoTax;
    protected DAOProduct daoProduct;
    protected DAOContext daoContext;

    protected Context context;

    @Inject
    public GenericInvoiceEntryBuilderImpl(TDAOEntry daoEntry, TDAOInvoice daoInvoice, DAOTax daoTax,
            DAOProduct daoProduct, DAOContext daoContext) {
        super(daoEntry);
        this.daoEntry = daoEntry;
        this.daoInvoice = daoInvoice;
        this.daoTax = daoTax;
        this.daoProduct = daoProduct;
        this.daoContext = daoContext;
    }

    @Override
    @NotOnUpdate
    public <T extends ShippingPoint> TBuilder setShippingOrigin(Builder<T> originBuilder) {
        BillyValidator.notNull(originBuilder,
                               GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.entry_shipping_origin"));
        this.getTypeInstance().setShippingOrigin(originBuilder.build());
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public <T extends ShippingPoint> TBuilder setShippingDestination(Builder<T> destinationBuilder) {
        BillyValidator.notNull(destinationBuilder,
                               GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.entry_shipping_destination"));
        this.getTypeInstance().setShippingDestination(destinationBuilder.build());
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setProductUID(StringID<Product> productUID) {
        BillyValidator.notNull(productUID, "field.product");
        ProductEntity p = this.daoProduct.get(productUID);
        BillyValidator.found(p, "field.product");
        this.getTypeInstance().setProduct(p);

        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setCurrency(Currency currency) {
        BillyValidator.notNull(currency, "field.currency");
        this.getTypeInstance().setCurrency(currency);

        return this.getBuilder();
    }

    @Override
    public <T extends Tax> TBuilder setTaxes(final Collection<T> taxes) {
        BillyValidator.notNull(taxes, "field.taxes");
        this.getTypeInstance().setTaxes(taxes);

        return this.getBuilder();
    }

    @Override
    public TBuilder setExternalID(final ExternalID<GenericInvoiceEntry> externalID) {
        BillyValidator.notNull(externalID, "field.external_id");
        this.getTypeInstance().setExternalID(externalID);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setQuantity(BigDecimal quantity) {
        Validate.isTrue(quantity.compareTo(BigDecimal.ZERO) > 0, "The quantity must be positive"); // TODO message
        this.getTypeInstance().setQuantity(quantity);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setUnitOfMeasure(String unit) {
        BillyValidator.notBlank(unit, "field.unit");
        this.getTypeInstance().setUnitOfMeasure(unit);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxPointDate(Date date) {
        BillyValidator.notNull(date, GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        this.getTypeInstance().setTaxPointDate(date);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder addDocumentReferenceUID(StringID<GenericInvoice> referenceUID) {
        BillyValidator.notNull(referenceUID, GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.reference"));
        GenericInvoiceEntity d = this.daoInvoice.get(referenceUID);
        BillyValidator.found(d, GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.reference"));
        this.getTypeInstance().getDocumentReferences().add(d);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setDescription(String description) {
        BillyValidator.notBlank(description, GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.description"));
        this.getTypeInstance().setDescription(description);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setShippingCostsAmount(BigDecimal amount) {
        BillyValidator.notNull(amount,
                               GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.shipping_costs_amount"));
        Validate.isTrue(amount.compareTo(BigDecimal.ZERO) >= 0);
        this.getTypeInstance().setShippingCostsAmount(amount);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setAmountType(AmountType type) {
        this.getTypeInstance().setAmountType(type);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setUnitAmount(AmountType type, BigDecimal amount) {
        BillyValidator.notNull(type, GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit_amount_type"));
        BillyValidator.notNull(amount, GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit_gross_amount"));

        switch (type) {
            case WITH_TAX:
                this.getTypeInstance().setUnitAmountWithTax(amount);
                this.getTypeInstance().setUnitAmountWithoutTax(null);
                break;
            case WITHOUT_TAX:
                this.getTypeInstance().setUnitAmountWithoutTax(amount);
                this.getTypeInstance().setUnitAmountWithTax(null);
                break;
        }
        this.getTypeInstance().setAmountType(type);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setContextUID(StringID<Context> uidContext) {
        BillyValidator.notNull(uidContext, GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.entry_context"));
        ContextEntity c = this.daoContext.get(uidContext);
        BillyValidator.found(c, GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.entry_context"));
        this.context = c;
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxExemptionReason(String exemptionReason) {
        BillyValidator.notBlank(exemptionReason,
                                GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_exemption_reason"));
        this.getTypeInstance().setTaxExemptionReason(exemptionReason);
        return this.getBuilder();
    }

    @Override
    @NotOnUpdate
    public TBuilder setTaxExemptionCode(String exemptionCode) {
        BillyValidator.notBlank(exemptionCode,
                                GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_exemption_code"));
        this.getTypeInstance().setTaxExemptionCode(exemptionCode);
        return this.getBuilder();
    }

    @NotImplemented
    @Deprecated
    @Override
    public TBuilder setDiscounts(DiscountType type, BigDecimal... discounts) {
        BillyValidator.notNull(type, GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.discount_type"));
        BillyValidator.notNull(discounts, GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.discount"));
        Validate.notEmpty(discounts, GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.discount_type"));

        return this.getBuilder();
    }

    @Override
    protected void validateInstance() throws BillyValidationException {
        GenericInvoiceEntry i = this.getTypeInstance();

        BillyValidator.mandatory(i.getTaxPointDate(),
                                 GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.tax_point_date"));
        BillyValidator.mandatory(i.getDescription(),
                                 GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.description"));
        BillyValidator.mandatory(i.getCurrency(), GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.currency"));

        this.validateValues();

        if (i.getAmountType().compareTo(AmountType.WITH_TAX) == 0) {
            BillyValidator.mandatory(i.getUnitAmountWithoutTax(),
                                     GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit_gross_amount"));
        } else {
            BillyValidator.mandatory(i.getUnitAmountWithoutTax(),
                                     GenericInvoiceEntryBuilderImpl.LOCALIZER.getString("field.unit_gross_amount"));
        }
    }

    protected void validateValues() {
        MathContext mc = BillyMathContext.get();

        GenericInvoiceEntryEntity e = this.getTypeInstance();

        if (e.getTaxes().isEmpty()) {
            for (Tax t : e.getProduct().getTaxes()) {
                if (this.daoContext.isSameOrSubContext(this.context, t.getContext())) {
                    Date taxDate = e.getTaxPointDate();
                    if (t.getValidTo() == null || DateUtils.isSameDay(t.getValidTo(), taxDate) || t.getValidTo().after(
                        taxDate)) {
                        e.getTaxes().add(t);
                    }
                }
            }

            if (e.getTaxes().isEmpty()) {
                throw new BillyValidationException(GenericInvoiceEntryBuilderImpl.LOCALIZER.getString(
                    "exception.invalid_taxes"));
            }
        }
        else {
            e.getTaxes().forEach(t -> {
                if (t.getValidTo() != null && t.getValidTo().before(e.getTaxPointDate())) {
                    throw new BillyValidationException(GenericInvoiceEntryBuilderImpl.LOCALIZER.getString(
                        "exception.invalid_taxes"));
                }
            });
        }

        e.setUnitDiscountAmount(BigDecimal.ZERO); // TODO

        if (e.getUnitAmountWithTax() != null) {
            BigDecimal unitAmountWithoutTax = e.getUnitAmountWithTax();
            BigDecimal unitTaxAmount = BigDecimal.ZERO;
            for (Tax t : this.getTypeInstance().getTaxes()) {
                switch (t.getTaxRateType()) {
                    case FLAT:
                        unitAmountWithoutTax = unitAmountWithoutTax.subtract(t.getValue(), mc);
                        unitTaxAmount = unitTaxAmount.add(t.getValue(), mc);
                        break;
                    case PERCENTAGE:
                        unitAmountWithoutTax = e.getUnitAmountWithTax()
                                .divide(BigDecimal.ONE.add(t.getPercentageRateValue().divide(new BigDecimal("100"), mc),
                                                           mc), mc);
                        unitTaxAmount =
                                unitTaxAmount.add(e.getUnitAmountWithTax().subtract(unitAmountWithoutTax, mc), mc);

                        break;
                    default:
                        break;
                }
            }
            e.setUnitAmountWithoutTax(unitAmountWithoutTax);
            e.setUnitTaxAmount(unitTaxAmount);

            // Minus discounts
            e.setUnitAmountWithoutTax(unitAmountWithoutTax.subtract(e.getUnitDiscountAmount(), mc));
        } else {
            BigDecimal unitAmountWithTax = e.getUnitAmountWithoutTax();
            BigDecimal unitTaxAmount = BigDecimal.ZERO;

            for (Tax t : this.getTypeInstance().getTaxes()) {
                switch (t.getTaxRateType()) {
                    case FLAT:
                        unitAmountWithTax = unitAmountWithTax.add(t.getValue(), mc);
                        unitTaxAmount = unitTaxAmount.add(t.getValue(), mc);
                        break;
                    case PERCENTAGE:
                        unitTaxAmount = unitTaxAmount.add(e.getUnitAmountWithoutTax()
                                                                  .multiply(t.getPercentageRateValue(), mc)
                                                                  .divide(new BigDecimal("100"), mc), mc);
                        unitAmountWithTax = unitAmountWithTax.add(unitTaxAmount, mc);
                        break;
                    default:
                        break;
                }
            }

            e.setUnitAmountWithTax(unitAmountWithTax);
            e.setUnitTaxAmount(unitTaxAmount);

        }

        e.setAmountWithTax(this.getTypeInstance().getUnitAmountWithTax().multiply(e.getQuantity(), mc));
        e.setAmountWithoutTax(this.getTypeInstance().getUnitAmountWithoutTax().multiply(e.getQuantity(), mc));
        e.setTaxAmount(this.getTypeInstance().getUnitTaxAmount().multiply(e.getQuantity(), mc));
        e.setDiscountAmount(this.getTypeInstance().getUnitDiscountAmount().multiply(e.getQuantity(), mc));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected GenericInvoiceEntryEntity getTypeInstance() {
        return super.getTypeInstance();
    }

}
