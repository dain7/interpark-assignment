package com.interpark.assignment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSearchLog is a Querydsl query type for SearchLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSearchLog extends EntityPathBase<SearchLog> {

    private static final long serialVersionUID = -2147164450L;

    public static final QSearchLog searchLog = new QSearchLog("searchLog");

    public final NumberPath<Long> cityId = createNumber("cityId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createDatetime = createDateTime("createDatetime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public QSearchLog(String variable) {
        super(SearchLog.class, forVariable(variable));
    }

    public QSearchLog(Path<? extends SearchLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSearchLog(PathMetadata metadata) {
        super(SearchLog.class, metadata);
    }

}

