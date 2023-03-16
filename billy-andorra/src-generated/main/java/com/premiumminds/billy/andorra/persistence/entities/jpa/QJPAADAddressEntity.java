package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QJPAADAddressEntity is a Querydsl query type for JPAADAddressEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADAddressEntity extends EntityPathBase<JPAADAddressEntity> {

    private static final long serialVersionUID = -1793601006L;

    public static final QJPAADAddressEntity jPAADAddressEntity = new QJPAADAddressEntity("jPAADAddressEntity");

    public final com.premiumminds.billy.persistence.entities.jpa.QJPAAddressEntity _super = new com.premiumminds.billy.persistence.entities.jpa.QJPAAddressEntity(this);

    //inherited
    public final BooleanPath active = _super.active;

    //inherited
    public final StringPath building = _super.building;

    //inherited
    public final StringPath city = _super.city;

    //inherited
    public final StringPath country = _super.country;

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp = _super.createTimestamp;

    //inherited
    public final StringPath details = _super.details;

    //inherited
    public final NumberPath<Integer> entityVersion = _super.entityVersion;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath number = _super.number;

    //inherited
    public final StringPath postalCode = _super.postalCode;

    //inherited
    public final StringPath region = _super.region;

    //inherited
    public final StringPath streetName = _super.streetName;

    //inherited
    public final StringPath uid = _super.uid;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp = _super.updateTimestamp;

    public QJPAADAddressEntity(String variable) {
        super(JPAADAddressEntity.class, forVariable(variable));
    }

    public QJPAADAddressEntity(Path<? extends JPAADAddressEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJPAADAddressEntity(PathMetadata metadata) {
        super(JPAADAddressEntity.class, metadata);
    }

}

