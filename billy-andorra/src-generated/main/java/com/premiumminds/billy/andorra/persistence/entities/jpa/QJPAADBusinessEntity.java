package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJPAADBusinessEntity is a Querydsl query type for JPAADBusinessEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADBusinessEntity extends EntityPathBase<JPAADBusinessEntity> {

    private static final long serialVersionUID = -1279183352L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJPAADBusinessEntity jPAADBusinessEntity = new QJPAADBusinessEntity("jPAADBusinessEntity");

    public final com.premiumminds.billy.persistence.entities.jpa.QJPABusinessEntity _super;

    //inherited
    public final BooleanPath active;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAAddressEntity address;

    //inherited
    public final ListPath<com.premiumminds.billy.persistence.entities.jpa.JPAApplicationEntity, com.premiumminds.billy.persistence.entities.jpa.QJPAApplicationEntity> applications;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAAddressEntity billingAddress;

    //inherited
    public final StringPath commercialName;

    //inherited
    public final ListPath<com.premiumminds.billy.persistence.entities.jpa.JPAContactEntity, com.premiumminds.billy.persistence.entities.jpa.QJPAContactEntity> contacts;

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp;

    //inherited
    public final NumberPath<Integer> entityVersion;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAContactEntity mainContact;

    //inherited
    public final StringPath name;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAContextEntity operationalContext;

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

    //inherited
    public final StringPath website;

    public QJPAADBusinessEntity(String variable) {
        this(JPAADBusinessEntity.class, forVariable(variable), INITS);
    }

    public QJPAADBusinessEntity(Path<? extends JPAADBusinessEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJPAADBusinessEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJPAADBusinessEntity(PathMetadata metadata, PathInits inits) {
        this(JPAADBusinessEntity.class, metadata, inits);
    }

    public QJPAADBusinessEntity(Class<? extends JPAADBusinessEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.premiumminds.billy.persistence.entities.jpa.QJPABusinessEntity(type, metadata, inits);
        this.active = _super.active;
        this.address = _super.address;
        this.applications = _super.applications;
        this.billingAddress = _super.billingAddress;
        this.commercialName = _super.commercialName;
        this.contacts = _super.contacts;
        this.createTimestamp = _super.createTimestamp;
        this.entityVersion = _super.entityVersion;
        this.id = _super.id;
        this.mainContact = _super.mainContact;
        this.name = _super.name;
        this.operationalContext = _super.operationalContext;
        this.shippingAddress = _super.shippingAddress;
        this.taxId = _super.taxId;
        this.taxIdISOCountryCode = _super.taxIdISOCountryCode;
        this.uid = _super.uid;
        this.updateTimestamp = _super.updateTimestamp;
        this.website = _super.website;
    }

}

