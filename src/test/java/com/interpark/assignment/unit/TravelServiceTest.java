package com.interpark.assignment.unit;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.domain.Member;
import com.interpark.assignment.domain.Travel;
import com.interpark.assignment.dto.travel.TravelRequestDto;
import com.interpark.assignment.dto.travel.TravelResponseDto;
import com.interpark.assignment.exception.EndDateBeforeOrSameStartDateException;
import com.interpark.assignment.exception.TravelNotFoundException;
import com.interpark.assignment.repository.CityRepository;
import com.interpark.assignment.repository.MemberRepository;
import com.interpark.assignment.repository.TravelRepository;
import com.interpark.assignment.service.MemberService;
import com.interpark.assignment.service.TravelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TravelServiceTest {
    @Mock
    TravelRepository travelRepository;
    @Mock
    CityRepository cityRepository;
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    TravelService travelService;

    @Test
    @DisplayName("여행 등록에 성공한다.")
    void create_travel_success_test() throws Exception {
        //given
        Long memberId = 1L;
        Member member = mock(Member.class);

        Long cityId = 1L;
        City city = mock(City.class);

        TravelRequestDto request = TravelRequestDto.builder()
                .cityId(cityId)
                .startDate(LocalDate.of(2023,1,1))
                .endDate(LocalDate.of(2023,12,31))
                .build();

        when(memberRepository.findById(memberId)).thenReturn(java.util.Optional.ofNullable(member));
        when(cityRepository.findById(cityId)).thenReturn(java.util.Optional.ofNullable(city));

        //when
        travelService.create(memberId, request);

        //then
        verify(travelRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("여행 끝 날짜가 시작 날짜보다 빨라, 여행 등록에 실패한다.")
    void create_travel_with_invalid_date_fail_test() throws Exception {
        //given
        Long memberId = 1L;
        Member member = mock(Member.class);

        Long cityId = 1L;
        City city = mock(City.class);

        TravelRequestDto request = TravelRequestDto.builder()
                .cityId(cityId)
                .startDate(LocalDate.of(2023,12,31))
                .endDate(LocalDate.of(2023,1,1))
                .build();

        when(memberRepository.findById(memberId)).thenReturn(java.util.Optional.ofNullable(member));
        when(cityRepository.findById(cityId)).thenReturn(java.util.Optional.ofNullable(city));

        // when & then
        Assertions.assertThrows(EndDateBeforeOrSameStartDateException.class, () -> {
            travelService.create(memberId, request);
        });
    }

    @Test
    @DisplayName("여행 수정 중 도시 수정에 성공한다.")
    void update_travel_success_test() throws Exception {
        //given
        Member member = mock(Member.class);

        City city = City.builder()
                .name("서울")
                .member(member)
                .build();
        Long updateCityId = 3L;
        City updateCity = City.builder()
                .name("부산")
                .member(member)
                .build();

        Long travelId = 3L;
        Travel travel = Travel.builder()
                .city(city)
                .startDate(LocalDate.of(2023,1,1))
                .endDate(LocalDate.of(2023,5,1))
                .build();

        TravelRequestDto request = TravelRequestDto.builder()
                .cityId(updateCityId)
                .startDate(LocalDate.of(2023,1,1))
                .endDate(LocalDate.of(2023,12,31))
                .build();

        when(travelRepository.findById(travelId)).thenReturn(java.util.Optional.ofNullable(travel));
        when(cityRepository.findById(updateCityId)).thenReturn(java.util.Optional.ofNullable(updateCity));

        // when
        travelService.update(travelId, request);

        // then
        Assertions.assertEquals(updateCity, travel.getCity());
    }

    @Test
    @DisplayName("여행 끝 날짜가 시작 날짜보다 빨라, 여행 수정에 실패한다.")
    void update_travel_with_invalid_date_fail_test() throws Exception {
        //given
        Member member = mock(Member.class);

        Long cityId = 1L;
        City city = City.builder()
                .name("서울")
                .member(member)
                .build();

        Long travelId = 2L;
        Travel travel = Travel.builder()
                .city(city)
                .startDate(LocalDate.of(2023,1,1))
                .endDate(LocalDate.of(2023,5,1))
                .build();

        TravelRequestDto request = TravelRequestDto.builder()
                .cityId(cityId)
                .startDate(LocalDate.of(2023,6,1))
                .endDate(LocalDate.of(2023,1,31))
                .build();

        when(travelRepository.findById(travelId)).thenReturn(java.util.Optional.ofNullable(travel));
        when(cityRepository.findById(cityId)).thenReturn(java.util.Optional.ofNullable(city));

        // when & then
        Assertions.assertThrows(EndDateBeforeOrSameStartDateException.class, () -> {
            travelService.update(travelId, request);
        });
    }

    @Test
    @DisplayName("여행 삭제에 성공한다.")
    void delete_travel_success_test() throws Exception {
        //given
        Travel travel = mock(Travel.class);
        Long travelId = 1L;

        when(travelRepository.findById(travelId)).thenReturn(java.util.Optional.ofNullable(travel));

        //when
        travelService.delete(travelId);

        //then
        verify(travelRepository, times(1)).delete(travel);
    }

    @Test
    @DisplayName("여행 정보가 존재하지 않아, 여행 삭제에 실패한다.")
    void delete_no_travel_fail_test() throws Exception {
        //given
        Long travelId = 1L;

        when(travelRepository.findById(travelId)).thenReturn(java.util.Optional.empty());

        //when & then
        Assertions.assertThrows(TravelNotFoundException.class, () -> {
            travelService.delete(travelId);
        });
    }

    @Test
    @DisplayName("단일 여행 조회에 성공한다.")
    void get_single_travel_success_test() throws Exception {
        //given
        Long travelId = 1L;
        Travel travel = mock(Travel.class);
        City city = mock(City.class);

        when(travelRepository.findByIdWithCity(travelId)).thenReturn(java.util.Optional.ofNullable(travel));
        when(travel.getId()).thenReturn(travelId);
        when(travel.getCity()).thenReturn(city);
        when(city.getName()).thenReturn("서울");
        when(travel.getStartDate()).thenReturn(LocalDate.of(2023, 1, 1));
        when(travel.getEndDate()).thenReturn(LocalDate.of(2023,12,1));

        //when
        TravelResponseDto response = travelService.get(travelId);

        //then
        Assertions.assertEquals(travelId, response.getId());
        Assertions.assertEquals("서울", response.getCityName());
        Assertions.assertEquals(LocalDate.of(2023, 1, 1), response.getStartDate());
        Assertions.assertEquals(LocalDate.of(2023,12,1), response.getEndDate());
    }

    @Test
    @DisplayName("여행 정보가 존재하지 않아, 단일 여행 조회에 성공한다.")
    void get_no_travel_fail_test() throws Exception {
        //given
        Long travelId = 1L;

        when(travelRepository.findByIdWithCity(travelId)).thenReturn(java.util.Optional.empty());

        //when
        Assertions.assertThrows(TravelNotFoundException.class, () -> {
            travelService.get(travelId);
        });
    }
}
