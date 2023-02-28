package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJPAADSupplierEntity is a Querydsl query type for JPAADSupplierEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADSupplierEntity extends EntityPathBase<JPAADSupplierEntity> {

    private static final long serialVersionUID = 871347988L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJPAADSupplierEntity jPAADSupplierEntity = new QJPAADSupplierEntity("jPAADSupplierEntity");

    public final com.premiumminds.billy.persistence.entities.jpa.QJPASupplierEntity _super;

    //inherited
    public final BooleanPath active;

    //inherited
    public final ListPath<com.premiumminds.billy.persistence.entities.jpa.JPAAddressEntity, com.premiumminds.billy.persistence.entities.jpa.QJPAAddressEntity> addresses;

    //inherited
    public final ListPath<com.premiumminds.billy.persistence.entities.jpa.JPABankAccountEntity, com.premiumminds.billy.persistence.entities.jpa.QJPABankAccountEntity> bankAccounts;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAAddressEntity billingAddress;

    //inherited
    public final ListPath<com.premiumminds.billy.persistence.entities.jpa.JPAContactEntity, com.premiumminds.billy.persistence.entities.jpa.QJPAContactEntity> contacts;

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp;

    //inherited
    public final NumberPath<Integer> entityVersion;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAAddressEntity mainAddress;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAContactEntity mainContact;

    //inherited
    public final StringPath name;

    public final StringPath referralName = createString("referralName");

    //inherited
    public final BooleanPath selfBillingAgreement;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAAddressEntity shippingAddress;

    //inherited
    public final StringPath taxRegistrationNumber;

    //inherited
    public final StringPath taxRegistrationNumberISOCountryCode;

    //inherited
    public final StringPath uid;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp;

    public QJPAADSupplierEntity(String variable) {
        this(JPAADSupplierEntity.class, forVariable(variable), INITS);
    }

    public QJPAADSupplierEntity(Path<? extends JPAADSupplierEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJPAADSupplierEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJPAADSupplierEntity(PathMetadata metadata, PathInits inits) {
        this(JPAADSupplierEntity.class, metadata, inits);
    }

    public QJPAADSupplierEntity(Class<? extends JPAADSupplierEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.premiumminds.billy.persistence.entities.jpa.QJPASupplierEntity(type, metadata, inits);
        this.active = _super.active;
        this.addresses = _super.addresses;
        this.bankAccounts = _super.bankAccounts;
        this.billingAddress = _super.billingAddress;
        this.contacts = _super.contacts;
        this.createTimestamp = _super.createTimestamp;
        this.entityVersion = _super.entityVersion;
        this.id = _super.id;
        this.mainAddress = _super.mainAddress;
        this.mainContact = _super.mainContact;
        this.name = _super.name;
        this.selfBillingAgreement = _super.selfBillingAgreement;
        this.shippingAddress = _super.shippingAddress;
        this.taxRegistrationNumber = _super.taxRegistrationNumber;
        this.taxRegistrationNumberISOCountryCode = _super.taxRegistrationNumberISOCountryCode;
        this.uid = _super.uid;
        this.updateTimestamp = _super.updateTimestamp;
    }

}

