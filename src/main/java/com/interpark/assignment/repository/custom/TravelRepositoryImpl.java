package com.interpark.assignment.repository.custom;

import com.interpark.assignment.domain.QCity;
import com.interpark.assignment.domain.QTravel;
import com.interpark.assignment.domain.Travel;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static com.interpark.assignment.domain.QCity.city;
import static com.interpark.assignment.domain.QTravel.travel;

public class TravelRepositoryImpl implements TravelRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public TravelRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<Travel> findByIdWithCity(Long travelId) {
        Travel resultTravel = queryFactory.select(travel)
                .from(travel)
                .join(travel.city, city)
                .fetchJoin()
                .fetchOne();
        return Optional.ofNullable(resultTravel);
    }
}
