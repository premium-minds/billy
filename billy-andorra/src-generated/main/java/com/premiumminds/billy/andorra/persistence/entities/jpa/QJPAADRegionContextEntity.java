package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJPAADRegionContextEntity is a Querydsl query type for JPAADRegionContextEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADRegionContextEntity extends EntityPathBase<JPAADRegionContextEntity> {

    private static final long serialVersionUID = -1826002759L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJPAADRegionContextEntity jPAADRegionContextEntity = new QJPAADRegionContextEntity("jPAADRegionContextEntity");

    public final com.premiumminds.billy.persistence.entities.jpa.QJPAContextEntity _super;

    //inherited
    public final BooleanPath active;

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp;

    //inherited
    public final StringPath description;

    //inherited
    public final NumberPath<Integer> entityVersion;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath name;

    // inherited
    public final com.premiumminds.billy.persistence.entities.jpa.QJPAContextEntity parent;

    public final SimplePath<com.premiumminds.billy.andorra.services.entities.ADRegionContext> parentContext = createSimple("parentContext", com.premiumminds.billy.andorra.services.entities.ADRegionContext.class);

    //inherited
    public final StringPath uid;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp;

    public QJPAADRegionContextEntity(String variable) {
        this(JPAADRegionContextEntity.class, forVariable(variable), INITS);
    }

    public QJPAADRegionContextEntity(Path<? extends JPAADRegionContextEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJPAADRegionContextEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJPAADRegionContextEntity(PathMetadata metadata, PathInits inits) {
        this(JPAADRegionContextEntity.class, metadata, inits);
    }

    public QJPAADRegionContextEntity(Class<? extends JPAADRegionContextEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.premiumminds.billy.persistence.entities.jpa.QJPAContextEntity(type, metadata, inits);
        this.active = _super.active;
        this.createTimestamp = _super.createTimestamp;
        this.description = _super.description;
        this.entityVersion = _super.entityVersion;
        this.id = _super.id;
        this.name = _super.name;
        this.parent = _super.parent;
        this.uid = _super.uid;
        this.updateTimestamp = _super.updateTimestamp;
    }

}

