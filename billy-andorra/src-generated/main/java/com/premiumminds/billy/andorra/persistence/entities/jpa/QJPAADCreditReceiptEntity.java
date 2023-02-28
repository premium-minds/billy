package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJPAADCreditReceiptEntity is a Querydsl query type for JPAADCreditReceiptEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADCreditReceiptEntity extends EntityPathBase<JPAADCreditReceiptEntity> {

    private static final long serialVersionUID = -1323612515L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJPAADCreditReceiptEntity jPAADCreditReceiptEntity = new QJPAADCreditReceiptEntity("jPAADCreditReceiptEntity");

    public final QJPAADGenericInvoiceEntity _super;

    //inherited
    public final BooleanPath active;

    //inherited
    public final NumberPath<java.math.BigDecimal> amountWithoutTax;

    //inherited
    public final NumberPath<java.math.BigDecimal> amountWithTax;

    //inherited
    public final StringPath batchId;

    //inherited
    public final BooleanPath billed;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPABusinessEntity business;

    //inherited
    public final BooleanPath cancelled;

    //inherited
    public final BooleanPath cashVATEndorser;

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp;

    //inherited
    public final EnumPath<com.premiumminds.billy.core.services.entities.documents.GenericInvoice.CreditOrDebit> creditOrDebit;

    //inherited
    public final SimplePath<java.util.Currency> currency;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPACustomerEntity customer;

    //inherited
    public final DateTimePath<java.util.Date> date;

    //inherited
    public final NumberPath<java.math.BigDecimal> discountsAmount;

    //inherited
    public final StringPath eacCode;

    //inherited
    public final NumberPath<Integer> entityVersion;

    public final ListPath<com.premiumminds.billy.andorra.services.entities.ADCreditReceiptEntry, SimplePath<com.premiumminds.billy.andorra.services.entities.ADCreditReceiptEntry>> entries = this.<com.premiumminds.billy.andorra.services.entities.ADCreditReceiptEntry, SimplePath<com.premiumminds.billy.andorra.services.entities.ADCreditReceiptEntry>>createList("entries", com.premiumminds.billy.andorra.services.entities.ADCreditReceiptEntry.class, SimplePath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.util.Date> generalLedgerDate;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath number;

    //inherited
    public final StringPath officeNumber;

    //inherited
    public final ListPath<com.premiumminds.billy.persistence.entities.jpa.JPAPaymentEntity, com.premiumminds.billy.persistence.entities.jpa.QJPAPaymentEntity> payments;

    //inherited
    public final StringPath paymentTerms;

    //inherited
    public final ListPath<String, StringPath> receiptNumbers;

    //inherited
    public final NumberPath<Integer> scale;

    //inherited
    public final BooleanPath selfBilled;

    //inherited
    public final StringPath series;

    //inherited
    public final NumberPath<Integer> seriesNumber;

    //inherited
    public final DateTimePath<java.util.Date> settlementDate;

    //inherited
    public final StringPath settlementDescription;

    //inherited
    public final NumberPath<java.math.BigDecimal> settlementDiscount;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAShippingPointEntity shippingDestination;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAShippingPointEntity shippingOrigin;

    //inherited
    public final StringPath sourceId;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPASupplierEntity supplier;

    //inherited
    public final NumberPath<java.math.BigDecimal> taxAmount;

    //inherited
    public final BooleanPath thirdPartyBilled;

    //inherited
    public final StringPath transactionId;

    //inherited
    public final StringPath uid;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp;

    public QJPAADCreditReceiptEntity(String variable) {
        this(JPAADCreditReceiptEntity.class, forVariable(variable), INITS);
    }

    public QJPAADCreditReceiptEntity(Path<? extends JPAADCreditReceiptEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJPAADCreditReceiptEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJPAADCreditReceiptEntity(PathMetadata metadata, PathInits inits) {
        this(JPAADCreditReceiptEntity.class, metadata, inits);
    }

    public QJPAADCreditReceiptEntity(Class<? extends JPAADCreditReceiptEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QJPAADGenericInvoiceEntity(type, metadata, inits);
        this.active = _super.active;
        this.amountWithoutTax = _super.amountWithoutTax;
        this.amountWithTax = _super.amountWithTax;
        this.batchId = _super.batchId;
        this.billed = _super.billed;
        this.business = _super.business;
        this.cancelled = _super.cancelled;
        this.cashVATEndorser = _super.cashVATEndorser;
        this.createTimestamp = _super.createTimestamp;
        this.creditOrDebit = _super.creditOrDebit;
        this.currency = _super.currency;
        this.customer = _super.customer;
        this.date = _super.date;
        this.discountsAmount = _super.discountsAmount;
        this.eacCode = _super.eacCode;
        this.entityVersion = _super.entityVersion;
        this.generalLedgerDate = _super.generalLedgerDate;
        this.id = _super.id;
        this.number = _super.number;
        this.officeNumber = _super.officeNumber;
        this.payments = _super.payments;
        this.paymentTerms = _super.paymentTerms;
        this.receiptNumbers = _super.receiptNumbers;
        this.scale = _super.scale;
        this.selfBilled = _super.selfBilled;
        this.series = _super.series;
        this.seriesNumber = _super.seriesNumber;
        this.settlementDate = _super.settlementDate;
        this.settlementDescription = _super.settlementDescription;
        this.settlementDiscount = _super.settlementDiscount;
        this.shippingDestination = _super.shippingDestination;
        this.shippingOrigin = _super.shippingOrigin;
        this.sourceId = _super.sourceId;
        this.supplier = _super.supplier;
        this.taxAmount = _super.taxAmount;
        this.thirdPartyBilled = _super.thirdPartyBilled;
        this.transactionId = _super.transactionId;
        this.uid = _super.uid;
        this.updateTimestamp = _super.updateTimestamp;
    }

}

