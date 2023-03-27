package com.interpark.assignment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCity is a Querydsl query type for City
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCity extends EntityPathBase<City> {

    private static final long serialVersionUID = -73643575L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCity city = new QCity("city");

    public final DateTimePath<java.time.LocalDateTime> createDatetime = createDateTime("createDatetime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath name = createString("name");

    public final ListPath<Travel, QTravel> travels = this.<Travel, QTravel>createList("travels", Travel.class, QTravel.class, PathInits.DIRECT2);

    public QCity(String variable) {
        this(City.class, forVariable(variable), INITS);
    }

    public QCity(Path<? extends City> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCity(PathMetadata metadata, PathInits inits) {
        this(City.class, metadata, inits);
    }

    public QCity(Class<? extends City> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

