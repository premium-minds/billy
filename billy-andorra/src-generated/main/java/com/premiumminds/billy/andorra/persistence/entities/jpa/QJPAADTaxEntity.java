package com.premiumminds.billy.andorra.persistence.entities.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QJPAADTaxEntity is a Querydsl query type for JPAADTaxEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJPAADTaxEntity extends EntityPathBase<JPAADTaxEntity> {

    private static final long serialVersionUID = -1334782455L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJPAADTaxEntity jPAADTaxEntity = new QJPAADTaxEntity("jPAADTaxEntity");

    public final com.premiumminds.billy.persistence.entities.jpa.QJPATaxEntity _super;

    //inherited
    public final BooleanPath active;

    //inherited
    public final StringPath code;

    public final SimplePath<com.premiumminds.billy.core.services.entities.Context> context = createSimple("context", com.premiumminds.billy.core.services.entities.Context.class);

    //inherited
    public final DateTimePath<java.util.Date> createTimestamp;

    //inherited
    public final SimplePath<java.util.Currency> currency;

    //inherited
    public final StringPath description;

    //inherited
    public final StringPath designation;

    //inherited
    public final NumberPath<Integer> entityVersion;

    //inherited
    public final NumberPath<java.math.BigDecimal> flatRateAmount;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<java.math.BigDecimal> percentageRateValue;

    //inherited
    public final EnumPath<com.premiumminds.billy.core.services.entities.Tax.TaxRateType> taxRateType;

    //inherited
    public final StringPath uid;

    //inherited
    public final DateTimePath<java.util.Date> updateTimestamp;

    //inherited
    public final DateTimePath<java.util.Date> validFrom;

    //inherited
    public final DateTimePath<java.util.Date> validTo;

    //inherited
    public final NumberPath<java.math.BigDecimal> value;

    public QJPAADTaxEntity(String variable) {
        this(JPAADTaxEntity.class, forVariable(variable), INITS);
    }

    public QJPAADTaxEntity(Path<? extends JPAADTaxEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJPAADTaxEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJPAADTaxEntity(PathMetadata metadata, PathInits inits) {
        this(JPAADTaxEntity.class, metadata, inits);
    }

    public QJPAADTaxEntity(Class<? extends JPAADTaxEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.premiumminds.billy.persistence.entities.jpa.QJPATaxEntity(type, metadata, inits);
        this.active = _super.active;
        this.code = _super.code;
        this.createTimestamp = _super.createTimestamp;
        this.currency = _super.currency;
        this.description = _super.description;
        this.designation = _super.designation;
        this.entityVersion = _super.entityVersion;
        this.flatRateAmount = _super.flatRateAmount;
        this.id = _super.id;
        this.percentageRateValue = _super.percentageRateValue;
        this.taxRateType = _super.taxRateType;
        this.uid = _super.uid;
        this.updateTimestamp = _super.updateTimestamp;
        this.validFrom = _super.validFrom;
        this.validTo = _super.validTo;
        this.value = _super.value;
    }

}

