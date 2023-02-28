package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJPAADPaymentEntity is a Querydsl query type for JPAADPaymentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADPaymentEntity extends EntityPathBase<JPAADPaymentEntity> {

    private static final long serialVersionUID = 377754276L;

    public static final QJPAADPaymentEntity jPAADPaymentEntity = new QJPAADPaymentEntity("jPAADPaymentEntity");

    public final com.premiumminds.billy.persistence.entities.jpa.QJPAPaymentEntity _super = new com.premiumminds.billy.persistence.entities.jpa.QJPAPaymentEntity(this);

    //inherited
    public final BooleanPath active = _super.active;

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp = _super.createTimestamp;

    //inherited
    public final NumberPath<Integer> entityVersion = _super.entityVersion;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<java.math.BigDecimal> paymentAmount = createNumber("paymentAmount", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.util.Date> paymentDate = _super.paymentDate;

    //inherited
    public final StringPath paymentMethod = _super.paymentMethod;

    //inherited
    public final StringPath uid = _super.uid;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp = _super.updateTimestamp;

    public QJPAADPaymentEntity(String variable) {
        super(JPAADPaymentEntity.class, forVariable(variable));
    }

    public QJPAADPaymentEntity(Path<? extends JPAADPaymentEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJPAADPaymentEntity(PathMetadata metadata) {
        super(JPAADPaymentEntity.class, metadata);
    }

}

