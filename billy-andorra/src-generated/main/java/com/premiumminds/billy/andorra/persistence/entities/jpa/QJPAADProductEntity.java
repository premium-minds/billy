package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJPAADProductEntity is a Querydsl query type for JPAADProductEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADProductEntity extends EntityPathBase<JPAADProductEntity> {

    private static final long serialVersionUID = -10085683L;

    public static final QJPAADProductEntity jPAADProductEntity = new QJPAADProductEntity("jPAADProductEntity");

    public final com.premiumminds.billy.persistence.entities.jpa.QJPAProductEntity _super = new com.premiumminds.billy.persistence.entities.jpa.QJPAProductEntity(this);

    //inherited
    public final BooleanPath active = _super.active;

    //inherited
    public final StringPath commodityCode = _super.commodityCode;

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp = _super.createTimestamp;

    //inherited
    public final StringPath description = _super.description;

    //inherited
    public final NumberPath<Integer> entityVersion = _super.entityVersion;

    //inherited
    public final StringPath group = _super.group;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath numberCode = _super.numberCode;

    //inherited
    public final StringPath productCode = _super.productCode;

    //inherited
    public final ListPath<com.premiumminds.billy.persistence.entities.jpa.JPATaxEntity, com.premiumminds.billy.persistence.entities.jpa.QJPATaxEntity> taxes = _super.taxes;

    //inherited
    public final EnumPath<com.premiumminds.billy.core.services.entities.Product.ProductType> type = _super.type;

    //inherited
    public final StringPath uid = _super.uid;

    //inherited
    public final StringPath unitOfMeasure = _super.unitOfMeasure;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp = _super.updateTimestamp;

    //inherited
    public final StringPath valuationMethod = _super.valuationMethod;

    public QJPAADProductEntity(String variable) {
        super(JPAADProductEntity.class, forVariable(variable));
    }

    public QJPAADProductEntity(Path<? extends JPAADProductEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJPAADProductEntity(PathMetadata metadata) {
        super(JPAADProductEntity.class, metadata);
    }

}

