package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJPAADShippingPointEntity is a Querydsl query type for JPAADShippingPointEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADShippingPointEntity extends EntityPathBase<JPAADShippingPointEntity> {

    private static final long serialVersionUID = 1205827552L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJPAADShippingPointEntity jPAADShippingPointEntity = new QJPAADShippingPointEntity("jPAADShippingPointEntity");

    public final com.premiumminds.billy.persistence.entities.jpa.QJPAShippingPointEntity _super;

    //inherited
    public final BooleanPath active;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAAddressEntity address;

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp;

    //inherited
    public final DateTimePath<java.util.Date> date;

    //inherited
    public final StringPath deliveryId;

    //inherited
    public final NumberPath<Integer> entityVersion;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath locationId;

    //inherited
    public final StringPath ucr;

    //inherited
    public final StringPath uid;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp;

    //inherited
    public final StringPath warehouseId;

    public QJPAADShippingPointEntity(String variable) {
        this(JPAADShippingPointEntity.class, forVariable(variable), INITS);
    }

    public QJPAADShippingPointEntity(Path<? extends JPAADShippingPointEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJPAADShippingPointEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJPAADShippingPointEntity(PathMetadata metadata, PathInits inits) {
        this(JPAADShippingPointEntity.class, metadata, inits);
    }

    public QJPAADShippingPointEntity(Class<? extends JPAADShippingPointEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.premiumminds.billy.persistence.entities.jpa.QJPAShippingPointEntity(type, metadata, inits);
        this.active = _super.active;
        this.address = _super.address;
        this.createTimestamp = _super.createTimestamp;
        this.date = _super.date;
        this.deliveryId = _super.deliveryId;
        this.entityVersion = _super.entityVersion;
        this.id = _super.id;
        this.locationId = _super.locationId;
        this.ucr = _super.ucr;
        this.uid = _super.uid;
        this.updateTimestamp = _super.updateTimestamp;
        this.warehouseId = _super.warehouseId;
    }

}

