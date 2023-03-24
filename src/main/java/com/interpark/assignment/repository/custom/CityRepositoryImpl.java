package com.interpark.assignment.repository.custom;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.domain.Travel;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.interpark.assignment.domain.QCity.city;
import static com.interpark.assignment.domain.QSearchLog.searchLog;
import static com.interpark.assignment.domain.QTravel.travel;

public class CityRepositoryImpl implements CityRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public CityRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<City> getCitiesByMember(Long memberId) {
        // 여행 중인 도시
        List<Travel> travels = queryFactory.select(travel)
                .from(travel)
                .join(travel.city, city)
                .fetchJoin()
                .where(
                        isNowAfterStartDate(),
                        isNowBeforeEndDate(),
                        eqMemberByTravel(memberId))
                .orderBy(travel.startDate.asc())
                .fetch();
        List<City> cities = new ArrayList<>(travels.stream().map(Travel::getCity).toList());

        if (cities.size() >= 10) {
            return cities;
        }

        // 여행이 예정된 도시
        List<City> soonCities = queryFactory.select(city)
                .from(travel)
                .join(travel.city, city)
                .where(
                        isSoonTravel(),
                        eqMemberByTravel(memberId),
                        notInCities(cities)
                )
                .orderBy(travel.startDate.asc())
                .fetch();
        cities.addAll(soonCities);

        // 하루 이내에 등록한 도시
        List<City> enteredCities = queryFactory.select(city)
                .from(city)
                .where(
                        isEnteredInOneDays(),
                        eqMemberByCity(memberId),
                        notInCities(cities)
                )
                .orderBy(city.createDatetime.desc())
                .fetch();
        cities.addAll(enteredCities);

        // 최근 일주일 이내에 한번 이상 조회한 도시
        List<City> searchedCities = queryFactory.select(city)
                .from(city)
                .leftJoin(searchLog).on(city.id.eq(searchLog.cityId))
                .where(
                        isSearchedIn7Days(),
                        eqMemberBySearchLog(memberId),
                        notInCities(cities)
                )
                .orderBy(searchLog.createDatetime.desc())
                .fetch();
        cities.addAll(searchedCities);

        // 나머지
        List<City> allCities = queryFactory.select(city)
                .from(city)
                .where(
                        notInCities(cities),
                        eqMemberByCity(memberId)
                )
                .fetch();
        cities.addAll(allCities);

        return cities.size() >= 10 ? cities.subList(0, 10) : cities;
    }

    private BooleanExpression notInCities(List<City> cities) {
        return city.notIn(cities);
    }

    private BooleanExpression eqMemberByCity(Long memberId) {
        return city.member.id.eq(memberId);
    }

    private BooleanExpression eqMemberByTravel(Long memberId) {
        return travel.member.id.eq(memberId);
    }


    private BooleanExpression eqMemberBySearchLog(Long memberId) {
        return searchLog.memberId.eq(memberId);
    }

    private BooleanExpression isNowAfterStartDate() {
        LocalDate now = LocalDate.now();
        return travel.startDate.loe(now);
    }

    private BooleanExpression isNowBeforeEndDate() {
        LocalDate now = LocalDate.now();
        return travel.endDate.goe(now);
    }

    private BooleanExpression isSoonTravel() {
        LocalDate now = LocalDate.now();
        return travel.startDate.gt(now);
    }

    private BooleanExpression isEnteredInOneDays() {
        LocalDateTime beforeOndDay = LocalDateTime.now().minusDays(1).withNano(0);
        return city.createDatetime.after(beforeOndDay);
    }

    private BooleanExpression isSearchedIn7Days() {
        LocalDateTime before7Days = LocalDateTime.now().minusDays(7).withNano(0);
        return searchLog.createDatetime.after(before7Days);
    }
}
