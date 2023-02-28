package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJPAADApplicationEntity is a Querydsl query type for JPAADApplicationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADApplicationEntity extends EntityPathBase<JPAADApplicationEntity> {

    private static final long serialVersionUID = -1421033234L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJPAADApplicationEntity jPAADApplicationEntity = new QJPAADApplicationEntity("jPAADApplicationEntity");

    public final com.premiumminds.billy.persistence.entities.jpa.QJPAApplicationEntity _super;

    //inherited
    public final BooleanPath active;

    public final ListPath<com.premiumminds.billy.core.services.entities.Contact, SimplePath<com.premiumminds.billy.core.services.entities.Contact>> contacts = this.<com.premiumminds.billy.core.services.entities.Contact, SimplePath<com.premiumminds.billy.core.services.entities.Contact>>createList("contacts", com.premiumminds.billy.core.services.entities.Contact.class, SimplePath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp;

    //inherited
    public final StringPath developerName;

    //inherited
    public final StringPath developerTaxId;

    //inherited
    public final StringPath developerTaxIdISOCountryCode;

    //inherited
    public final NumberPath<Integer> entityVersion;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAContactEntity mainContact;

    //inherited
    public final StringPath name;

    //inherited
    public final StringPath uid;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp;

    //inherited
    public final StringPath version;

    //inherited
    public final StringPath website;

    public QJPAADApplicationEntity(String variable) {
        this(JPAADApplicationEntity.class, forVariable(variable), INITS);
    }

    public QJPAADApplicationEntity(Path<? extends JPAADApplicationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJPAADApplicationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJPAADApplicationEntity(PathMetadata metadata, PathInits inits) {
        this(JPAADApplicationEntity.class, metadata, inits);
    }

    public QJPAADApplicationEntity(Class<? extends JPAADApplicationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.premiumminds.billy.persistence.entities.jpa.QJPAApplicationEntity(type, metadata, inits);
        this.active = _super.active;
        this.createTimestamp = _super.createTimestamp;
        this.developerName = _super.developerName;
        this.developerTaxId = _super.developerTaxId;
        this.developerTaxIdISOCountryCode = _super.developerTaxIdISOCountryCode;
        this.entityVersion = _super.entityVersion;
        this.id = _super.id;
        this.mainContact = _super.mainContact;
        this.name = _super.name;
        this.uid = _super.uid;
        this.updateTimestamp = _super.updateTimestamp;
        this.version = _super.version;
        this.website = _super.website;
    }

}

