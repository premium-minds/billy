package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJPAADCustomerEntity is a Querydsl query type for JPAADCustomerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADCustomerEntity extends EntityPathBase<JPAADCustomerEntity> {

    private static final long serialVersionUID = -1364662362L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJPAADCustomerEntity jPAADCustomerEntity = new QJPAADCustomerEntity("jPAADCustomerEntity");

    public final com.premiumminds.billy.persistence.entities.jpa.QJPACustomerEntity _super;

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
    public final BooleanPath selfBilling;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAAddressEntity shippingAddress;

    //inherited
    public final StringPath taxId;

    //inherited
    public final StringPath taxIdISOCountryCode;

    //inherited
    public final StringPath uid;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp;

    public QJPAADCustomerEntity(String variable) {
        this(JPAADCustomerEntity.class, forVariable(variable), INITS);
    }

    public QJPAADCustomerEntity(Path<? extends JPAADCustomerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJPAADCustomerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJPAADCustomerEntity(PathMetadata metadata, PathInits inits) {
        this(JPAADCustomerEntity.class, metadata, inits);
    }

    public QJPAADCustomerEntity(Class<? extends JPAADCustomerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.premiumminds.billy.persistence.entities.jpa.QJPACustomerEntity(type, metadata, inits);
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
        this.selfBilling = _super.selfBilling;
        this.shippingAddress = _super.shippingAddress;
        this.taxId = _super.taxId;
        this.taxIdISOCountryCode = _super.taxIdISOCountryCode;
        this.uid = _super.uid;
        this.updateTimestamp = _super.updateTimestamp;
    }

}

