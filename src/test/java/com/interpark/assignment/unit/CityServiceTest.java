package com.interpark.assignment.unit;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.domain.Member;
import com.interpark.assignment.domain.Travel;
import com.interpark.assignment.dto.city.CityGetResponseDto;
import com.interpark.assignment.dto.city.CityRequestDto;
import com.interpark.assignment.dto.city.CityResponseDto;
import com.interpark.assignment.exception.CityCannotDeleteException;
import com.interpark.assignment.exception.CityNotFoundException;
import com.interpark.assignment.exception.MemberNotFoundException;
import com.interpark.assignment.repository.CityRepository;
import com.interpark.assignment.repository.MemberRepository;
import com.interpark.assignment.service.CityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    CityRepository cityRepository;
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    CityService cityService;

    @Test
    @DisplayName("도시 등록에 성공한다.")
    void create_city_success_test() throws Exception {
        //given
        Long memberId = 1L;
        Member member = mock(Member.class);

        CityRequestDto request = CityRequestDto.builder()
                .name("서울")
                .build();

        when(memberRepository.findById(memberId)).thenReturn(ofNullable(member));

        //when
        cityService.create(memberId, request);

        //then
        verify(cityRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("도시를 등록하는데, 존재하지 않는 멤버일 경우 실패한다.")
    void create_city_with_no_member_fail_test() throws Exception {
        //given
        Long memberId = 999L;

        CityRequestDto request = CityRequestDto.builder()
                .name("서울")
                .build();

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        //when & then
        Assertions.assertThrows(
                MemberNotFoundException.class, () -> {
                    cityService.create(memberId, request);
                }
        );
    }

    @Test
    @DisplayName("도시 이름 수정에 성공한다.")
    void update_city_name_success_test() throws Exception {
        //given
        Member member = mock(Member.class);

        Long cityId = 1L;
        City city = City.builder()
                .name("부산")
                .member(member)
                .build();

        CityRequestDto request = CityRequestDto.builder()
                .name("서울")
                .build();

        when(cityRepository.findById(cityId)).thenReturn(ofNullable(city));

        //when
        cityService.update(cityId, request);

        //then
        Assertions.assertEquals("서울", city.getName());
    }

    @Test
    @DisplayName("도시가 존재하지 않아 도시 수정에 실패한다.")
    void update_no_city_fail_test() throws Exception {
        //given
        Long cityId = 1L;

        CityRequestDto request = CityRequestDto.builder()
                .name("서울")
                .build();

        when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

        //when & then
        Assertions.assertThrows(
                CityNotFoundException.class, () -> {
                    cityService.update(cityId, request);
                }
        );
    }

    @Test
    @DisplayName("도시 삭제에 성공한다.")
    void delete_city_success_test() throws Exception {
        //given
        Long cityId = 1L;
        City city = mock(City.class);

        when(cityRepository.findById(cityId)).thenReturn(ofNullable(city));

        // when
        cityService.delete(cityId);

        //then
        verify(cityRepository, times(1)).delete(city);
    }

    @Test
    @DisplayName("도시가 존재하지 않아, 도시 삭제에 실패한다.")
    void delete_no_city_fail_test() throws Exception {
        //given
        Long cityId = 1L;
        when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

        //when & then
        Assertions.assertThrows(
                CityNotFoundException.class, () -> {
                    cityService.delete(cityId);
                }
        );
    }

    @Test
    @DisplayName("여행에 등록된 도시라, 도시 삭제에 실패한다.")
    void delete_city_with_travel_fail_test() throws Exception {
        //given
        Long cityId = 1L;
        City city = mock(City.class);
        Travel travel = mock(Travel.class);

        when(cityRepository.findById(cityId)).thenReturn(ofNullable(city));
        when(city.getTravels()).thenReturn(List.of(travel));

        //when & then
        Assertions.assertThrows(
                CityCannotDeleteException.class, () -> {
                    cityService.delete(cityId);
                }
        );
    }

    @Test
    @DisplayName("단일 도시 조회에 성공한다.")
    void get_single_city_success_test() throws Exception {
        //given
        Long cityId = 1L;
        String cityName = "서울";
        City city = mock(City.class);

        when(cityRepository.findById(cityId)).thenReturn(ofNullable(city));
        when(city.getId()).thenReturn(cityId);
        when(city.getName()).thenReturn(cityName);

        //when
        CityResponseDto response = cityService.get(cityId);

        //then
        Assertions.assertEquals(cityId, response.getId());
        Assertions.assertEquals(cityName, response.getName());
    }

    @Test
    @DisplayName("도시가 존재하지 않아, 도시 조회에 실패한다.")
    void get_no_city_fail_test() throws Exception {
        //given
        Long cityId = 1L;

        when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

        //when & then
        Assertions.assertThrows(
                CityNotFoundException.class, () -> {
                    cityService.get(cityId);
                }
        );
    }

    @Test
    @DisplayName("사용자 도시 목록 조회에 성공한다.")
    void get_cities_by_user_success_test() throws Exception {
        //given
        Long memberId = 1L;
        Member member = mock(Member.class);

        Long seoulId = 1L;
        Long jejuId = 2L;
        Long daeguId = 3L;

        City seoul = mock(City.class);
        City jeju = mock(City.class);
        City daegu = mock(City.class);

        when(memberRepository.findById(memberId)).thenReturn(Optional.ofNullable(member));
        when(cityRepository.getCitiesByMember(memberId)).thenReturn(List.of(seoul, jeju, daegu));
        when(seoul.getId()).thenReturn(seoulId);
        when(seoul.getName()).thenReturn("서울");
        when(jeju.getId()).thenReturn(jejuId);
        when(jeju.getName()).thenReturn("제주");
        when(daegu.getId()).thenReturn(daeguId);
        when(daegu.getName()).thenReturn("대구");

        //when
        List<CityGetResponseDto> response = cityService.getByMember(memberId);

        // then
        Assertions.assertEquals(seoulId, response.get(0).getId());
        Assertions.assertEquals("서울", response.get(0).getName());
        Assertions.assertEquals(jejuId, response.get(1).getId());
        Assertions.assertEquals("제주", response.get(1).getName());
        Assertions.assertEquals(daeguId, response.get(2).getId());
        Assertions.assertEquals("대구", response.get(2).getName());
    }

    @Test
    @DisplayName("멤버가 존재하지 않아, 사용자 도시 목록 조회에 실패한다.")
    void get_cities_by_wrong_user_fail_test() throws Exception {
        //given
        Long memberId = 1L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        //when & then

        Assertions.assertThrows(
                MemberNotFoundException.class, () -> {
                    cityService.getByMember(memberId);
                }
        );
    }
}
