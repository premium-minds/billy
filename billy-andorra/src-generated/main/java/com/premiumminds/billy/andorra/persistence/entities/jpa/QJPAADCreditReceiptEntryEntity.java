package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJPAADCreditReceiptEntryEntity is a Querydsl query type for JPAADCreditReceiptEntryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADCreditReceiptEntryEntity extends EntityPathBase<JPAADCreditReceiptEntryEntity> {

    private static final long serialVersionUID = -1545896837L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJPAADCreditReceiptEntryEntity jPAADCreditReceiptEntryEntity = new QJPAADCreditReceiptEntryEntity("jPAADCreditReceiptEntryEntity");

    public final QJPAADGenericInvoiceEntryEntity _super;

    //inherited
    public final BooleanPath active;

    //inherited
    public final NumberPath<java.math.BigDecimal> amountWithoutTax;

    //inherited
    public final NumberPath<java.math.BigDecimal> amountWithTax;

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp;

    //inherited
    public final EnumPath<com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit> creditOrDebit;

    //inherited
    public final SimplePath<java.util.Currency> currency;

    //inherited
    public final StringPath description;

    //inherited
    public final NumberPath<java.math.BigDecimal> discountAmount;

    //inherited
    public final NumberPath<Integer> entityVersion;

    //inherited
    public final NumberPath<java.math.BigDecimal> exchangeRateToDocumentCurrency;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<Integer> number;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAProductEntity product;

    //inherited
    public final NumberPath<java.math.BigDecimal> quantity;

    public final StringPath reason = createString("reason");

    public final QJPAADReceiptEntity receiptReference;

    //inherited
    public final ListPath<com.premiumminds.billy.persistence.entities.jpa.JPAGenericInvoiceEntity, com.premiumminds.billy.persistence.entities.jpa.QJPAGenericInvoiceEntity> references;

    //inherited
    public final NumberPath<java.math.BigDecimal> shippingCostsAmount;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAShippingPointEntity shippingDestination;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAShippingPointEntity shippingOrigin;

    //inherited
    public final NumberPath<java.math.BigDecimal> taxAmount;

    //inherited
    public final ListPath<com.premiumminds.billy.persistence.entities.jpa.JPATaxEntity, com.premiumminds.billy.persistence.entities.jpa.QJPATaxEntity> taxes;

    //inherited
    public final StringPath taxExemptionCode;

    //inherited
    public final StringPath taxExemptionReason;

    //inherited
    public final DateTimePath<java.util.Date> taxPointDate;

    //inherited
    public final EnumPath<com.premiumminds.billy.core.services.builders.GenericInvoiceEntryBuilder.AmountType> type;

    //inherited
    public final StringPath uid;

    //inherited
    public final NumberPath<java.math.BigDecimal> unitAmountWithoutTax;

    //inherited
    public final NumberPath<java.math.BigDecimal> unitAmountWithTax;

    //inherited
    public final NumberPath<java.math.BigDecimal> unitDiscountAmount;

    //inherited
    public final StringPath unitOfMeasure;

    //inherited
    public final NumberPath<java.math.BigDecimal> unitTaxAmount;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp;

    public QJPAADCreditReceiptEntryEntity(String variable) {
        this(JPAADCreditReceiptEntryEntity.class, forVariable(variable), INITS);
    }

    public QJPAADCreditReceiptEntryEntity(Path<? extends JPAADCreditReceiptEntryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJPAADCreditReceiptEntryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJPAADCreditReceiptEntryEntity(PathMetadata metadata, PathInits inits) {
        this(JPAADCreditReceiptEntryEntity.class, metadata, inits);
    }

    public QJPAADCreditReceiptEntryEntity(Class<? extends JPAADCreditReceiptEntryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QJPAADGenericInvoiceEntryEntity(type, metadata, inits);
        this.active = _super.active;
        this.amountWithoutTax = _super.amountWithoutTax;
        this.amountWithTax = _super.amountWithTax;
        this.createTimestamp = _super.createTimestamp;
        this.creditOrDebit = _super.creditOrDebit;
        this.currency = _super.currency;
        this.description = _super.description;
        this.discountAmount = _super.discountAmount;
        this.entityVersion = _super.entityVersion;
        this.exchangeRateToDocumentCurrency = _super.exchangeRateToDocumentCurrency;
        this.id = _super.id;
        this.number = _super.number;
        this.product = _super.product;
        this.quantity = _super.quantity;
        this.receiptReference = inits.isInitialized("receiptReference") ? new QJPAADReceiptEntity(forProperty("receiptReference"), inits.get("receiptReference")) : null;
        this.references = _super.references;
        this.shippingCostsAmount = _super.shippingCostsAmount;
        this.shippingDestination = _super.shippingDestination;
        this.shippingOrigin = _super.shippingOrigin;
        this.taxAmount = _super.taxAmount;
        this.taxes = _super.taxes;
        this.taxExemptionCode = _super.taxExemptionCode;
        this.taxExemptionReason = _super.taxExemptionReason;
        this.taxPointDate = _super.taxPointDate;
        this.type = _super.type;
        this.uid = _super.uid;
        this.unitAmountWithoutTax = _super.unitAmountWithoutTax;
        this.unitAmountWithTax = _super.unitAmountWithTax;
        this.unitDiscountAmount = _super.unitDiscountAmount;
        this.unitOfMeasure = _super.unitOfMeasure;
        this.unitTaxAmount = _super.unitTaxAmount;
        this.updateTimestamp = _super.updateTimestamp;
    }

}

