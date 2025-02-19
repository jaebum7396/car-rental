package com.rental.domain.car;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCar is a Querydsl query type for Car
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCar extends EntityPathBase<Car> {

    private static final long serialVersionUID = 2143418573L;

    public static final QCar car = new QCar("car");

    public final com.rental.common.QBaseTimeEntity _super = new com.rental.common.QBaseTimeEntity(this);

    public final BooleanPath available = createBoolean("available");

    public final SetPath<Category, QCategory> categories = this.<Category, QCategory>createSet("categories", Category.class, QCategory.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath manufacturer = createString("manufacturer");

    public final StringPath model = createString("model");

    public final NumberPath<Integer> productionYear = createNumber("productionYear", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCar(String variable) {
        super(Car.class, forVariable(variable));
    }

    public QCar(Path<? extends Car> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCar(PathMetadata metadata) {
        super(Car.class, metadata);
    }

}

