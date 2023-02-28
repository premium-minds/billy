package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJPAADContactEntity is a Querydsl query type for JPAADContactEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADContactEntity extends EntityPathBase<JPAADContactEntity> {

    private static final long serialVersionUID = -435975874L;

    public static final QJPAADContactEntity jPAADContactEntity = new QJPAADContactEntity("jPAADContactEntity");

    public final com.premiumminds.billy.persistence.entities.jpa.QJPAContactEntity _super = new com.premiumminds.billy.persistence.entities.jpa.QJPAContactEntity(this);

    //inherited
    public final BooleanPath active = _super.active;

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp = _super.createTimestamp;

    //inherited
    public final StringPath email = _super.email;

    //inherited
    public final NumberPath<Integer> entityVersion = _super.entityVersion;

    //inherited
    public final StringPath fax = _super.fax;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath mobile = _super.mobile;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath phone = _super.phone;

    //inherited
    public final StringPath uid = _super.uid;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp = _super.updateTimestamp;

    //inherited
    public final StringPath website = _super.website;

    public QJPAADContactEntity(String variable) {
        super(JPAADContactEntity.class, forVariable(variable));
    }

    public QJPAADContactEntity(Path<? extends JPAADContactEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJPAADContactEntity(PathMetadata metadata) {
        super(JPAADContactEntity.class, metadata);
    }

}

