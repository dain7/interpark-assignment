package com.interpark.assignment.repository;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.domain.Member;
import com.interpark.assignment.domain.SearchLog;
import com.interpark.assignment.domain.Travel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TravelRepository travelRepository;
    @Autowired
    SearchLogRepository searchLogRepository;

    @Test
    @DisplayName("현재 여행 중인 도시 목록을 여행 시작일 순으로 가져온다. (중복 허용)")
    void get_traveling_cities_test() throws Exception {
        //given
        Member member = Member.builder().name("인터파크").build();
        memberRepository.save(member);

        City seoul = City.builder()
                .member(member)
                .name("서울")
                .build();
        City daejeon = City.builder()
                .member(member)
                .name("대전")
                .build();
        City jeju = City.builder()
                .member(member)
                .name("제주")
                .build();
        cityRepository.saveAll(List.of(seoul, daejeon, jeju));

        Travel seoulTravel = Travel.builder()
                .member(member)
                .city(seoul)
                .startDate(LocalDate.of(2023, 3, 1))
                .endDate(LocalDate.of(2023, 5, 1))
                .build();
        Travel daejeonTravel = Travel.builder()
                .member(member)
                .city(daejeon)
                .startDate(LocalDate.of(2023, 3, 21))
                .endDate(LocalDate.of(2023, 4, 28))
                .build();
        Travel jejuTravel = Travel.builder()
                .member(member)
                .city(jeju)
                .startDate(LocalDate.of(2023, 3, 5))
                .endDate(LocalDate.of(2023, 3, 31))
                .build();
        Travel jejuTravel2 = Travel.builder()
                .member(member)
                .city(jeju)
                .startDate(LocalDate.of(2023, 3, 23))
                .endDate(LocalDate.of(2023, 3, 31))
                .build();
        travelRepository.saveAll(List.of(seoulTravel, daejeonTravel, jejuTravel, jejuTravel2));

        //when
        List<City> cities = cityRepository.getCitiesByMember(member.getId());

        //then
        Assertions.assertEquals(List.of(seoul, jeju, daejeon, jeju), cities);
    }
    
    @Test
    @DisplayName("곧 여행이 시작될 도시들을 여행 시작일 순으로 가져온다. (중복 비허용)")
    void get_soon_travel_cities_test() throws Exception {
        //given
        Member member = Member.builder().name("인터파크").build();
        memberRepository.save(member);

        City seoul = City.builder()
                .member(member)
                .name("서울")
                .build();
        City daejeon = City.builder()
                .member(member)
                .name("대전")
                .build();
        City jeju = City.builder()
                .member(member)
                .name("제주")
                .build();
        cityRepository.saveAll(List.of(seoul, daejeon, jeju));

        Travel seoulTravel = Travel.builder()
                .member(member)
                .city(seoul)
                .startDate(LocalDate.of(2023, 4, 5))
                .endDate(LocalDate.of(2023, 5, 1))
                .build();
        Travel daejeonTravel = Travel.builder()
                .member(member)
                .city(daejeon)
                .startDate(LocalDate.of(2023, 5, 5))
                .endDate(LocalDate.of(2023, 6, 10))
                .build();
        Travel jejuTravel = Travel.builder()
                .member(member)
                .city(jeju)
                .startDate(LocalDate.of(2023, 4, 27))
                .endDate(LocalDate.of(2023, 7, 31))
                .build();
        Travel jejuTravel2 = Travel.builder()
                .member(member)
                .city(jeju)
                .startDate(LocalDate.of(2023, 5, 8))
                .endDate(LocalDate.of(2023, 9, 30))
                .build();
        travelRepository.saveAll(List.of(seoulTravel, daejeonTravel, jejuTravel, jejuTravel2));

        //when
        List<City> cities = cityRepository.getCitiesByMember(member.getId());

        //then
        Assertions.assertEquals(List.of(seoul, jeju, daejeon), cities);
    }

    @Test
    @DisplayName("하루 이내에 등록된 도시들을 최근순으로 가져온다.")
    void get_created_cities_in_ond_day_test() throws Exception {
        //given
        Member member = Member.builder().name("인터파크").build();
        memberRepository.save(member);

        City seoul = City.builder()
                .member(member)
                .name("서울")
                .build();
        City daejeon = City.builder()
                .member(member)
                .name("대전")
                .build();
        City jeju = City.builder()
                .member(member)
                .name("제주")
                .build();
        cityRepository.saveAll(List.of(seoul, daejeon, jeju));

        //when
        List<City> cities = cityRepository.getCitiesByMember(member.getId());

        //then
        Assertions.assertEquals(List.of(jeju, daejeon, seoul), cities);
    }
    
    @Test
    @DisplayName("7일 이내 조회된 도시들을 가장 최근 조회된 순으로 가져온다.")
    void get_cities_by_searched_in_7_days_test() throws Exception {
        //given
        Member member = Member.builder().name("인터파크").build();
        memberRepository.save(member);

        City seoul = City.builder()
                .member(member)
                .name("서울")
                .build();
        City daejeon = City.builder()
                .member(member)
                .name("대전")
                .build();
        City jeju = City.builder()
                .member(member)
                .name("제주")
                .build();
        cityRepository.saveAll(List.of(seoul, daejeon, jeju));

        SearchLog seoulLog = SearchLog.builder()
                .memberId(member.getId())
                .cityId(seoul.getId())
                .build();
        SearchLog daejeonLog = SearchLog.builder()
                .memberId(member.getId())
                .cityId(daejeon.getId())
                .build();
        SearchLog jejuLog = SearchLog.builder()
                .memberId(member.getId())
                .cityId(jeju.getId())
                .build();
        searchLogRepository.saveAll(List.of(seoulLog, daejeonLog, jejuLog));

        //when
        List<City> cities = cityRepository.getCitiesByMember(member.getId());
        
        //then
        Assertions.assertEquals(List.of(jeju, daejeon, seoul), cities);
    }
}
