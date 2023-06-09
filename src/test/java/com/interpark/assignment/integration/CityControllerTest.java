package com.interpark.assignment.integration;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.dto.city.CityRequestDto;
import com.interpark.assignment.exception.ErrorCode;
import com.interpark.assignment.repository.CityRepository;
import com.interpark.assignment.repository.SearchLogRepository;
import com.interpark.assignment.setUp.CitySetUp;
import com.interpark.assignment.setUp.MemberSetUp;
import com.interpark.assignment.setUp.TravelSetUp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityControllerTest extends BaseIntegrationTest {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private SearchLogRepository searchLogRepository;
    @Autowired
    private MemberSetUp memberSetUp;
    @Autowired
    private CitySetUp citySetUp;
    @Autowired
    private TravelSetUp travelSetUp;

    @Test
    @DisplayName("도시 등록에 성공한다.")
    void create_city_test() throws Exception {
        //given
        Long memberId = memberSetUp.getMemberId("인터파크");
        CityRequestDto request = CityRequestDto.builder()
                .name("서울")
                .build();

        //when
        mvc.perform(post("/city")
                        .header("member-id", memberId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists());
    }

    @Test
    @DisplayName("도시 수정에 성공한다.")
    void update_city_test() throws Exception {
        //given
        Long memberId = memberSetUp.getMemberId("인터파크");
        Long cityId = citySetUp.getCityId(memberId, "서울");

        CityRequestDto request = CityRequestDto.builder()
                .name("부산")
                .build();

        //when
        mvc.perform(post("/city/{cityId}", cityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                )
                .andDo(print())
                .andExpect(status().isOk());

        // then
        City city = cityRepository.findById(cityId).orElse(null);
        assert city != null;
        Assertions.assertEquals("부산", city.getName());
    }

    @Test
    @DisplayName("여행에 지정되지 않은 도시 삭제에 성공한다.")
    void delete_city_test() throws Exception {
        //given
        Long memberId = memberSetUp.getMemberId("인터파크");
        Long cityId = citySetUp.getCityId(memberId, "서울");

        //when
        mvc.perform(delete("/city/{cityId}", cityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        // then
        City city = cityRepository.findById(cityId).orElse(null);
        Assertions.assertNull(city);
    }

    @Test
    @DisplayName("여행에 지정된 도시 삭제에 실패한다.")
    void delete_city_with_travel_test() throws Exception {
        //given
        Long memberId = memberSetUp.getMemberId("인터파크");
        Long cityId = citySetUp.getCityId(memberId, "서울");
        travelSetUp.getTravelId(memberId, cityId, LocalDate.of(2023,3,1), LocalDate.of(2323, 5, 1));

        //when
        mvc.perform(delete("/city/{cityId}", cityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.CITY_CANNOT_DELETE_EXCEPTION.getMessage()));
    }

    @Test
    @DisplayName("단일 도시 정보 조회에 성공한다.")
    void get_city_test() throws Exception {
        //given
        Long memberId = memberSetUp.getMemberId("인터파크");
        Long cityId = citySetUp.getCityId(memberId, "서울");

        //when
        mvc.perform(get("/city/{cityId}", cityId)
                        .header("member-id", memberId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.cities.id").exists())
                .andExpect(jsonPath("$.data.cities.name").value("서울"));
    }

    @Test
    @DisplayName("사용자별 도시 조회에 성공한다.")
    void get_city_by_member_test() throws Exception {
        Long memberId = memberSetUp.getMemberId("인터파크");

        /**
         * 1. 여행 중인 도시 데이터 생성 (중복 허용, 여행 시작일 순 정렬) : 서울, 전주, 서울
         */

        Long seoulId = citySetUp.getCityId(memberId, "서울");
        Long jeonjuId = citySetUp.getCityId(memberId, "전주");

        travelSetUp.getTravelId(memberId, seoulId, LocalDate.of(2023,2,1), LocalDate.of(2323, 4, 1));
        travelSetUp.getTravelId(memberId, seoulId, LocalDate.of(2023,3,20), LocalDate.of(2323, 4, 1));
        travelSetUp.getTravelId(memberId, jeonjuId, LocalDate.of(2023,3,1), LocalDate.of(2323, 4, 1));

        /**
         * 2. 여행이 예정된 도시 (시작일 순) : 대구, 광주
         */
        Long daeguId = citySetUp.getCityId(memberId, "대구");
        Long gwangjuId = citySetUp.getCityId(memberId, "광주");

        travelSetUp.getTravelId(memberId, daeguId, LocalDate.of(2023,4,1), LocalDate.of(2323, 4, 5));
        travelSetUp.getTravelId(memberId, gwangjuId, LocalDate.of(2023,5,10), LocalDate.of(2323, 6, 1));

        /**
         *  3. 하루 이내에 등록된 도시 (최근 등록순) : 인천, 대전
         */
        citySetUp.getCityId(memberId, "대전");
        citySetUp.getCityId(memberId, "인천");

        /**
         * 4. 최근 일주일 이내에 한번 이상 조회된 도시 : 제주
         */
        Long jejuId = citySetUp.getCityId(memberId, "제주");
        City jeju = cityRepository.findById(jejuId).orElse(null);
        jeju.updateCreateTime(LocalDateTime.of(2019, 2, 22, 13, 0));

        // 조회 API Call
        mvc.perform(get("/city/{cityId}", jejuId)
                        .header("member-id", memberId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());


        /**
         * 5. 나머지 도시 : 강릉, 경주, 울산
         */
        Long gangnenungId = citySetUp.getCityId(memberId, "강릉");
        City gangneung = cityRepository.findById(gangnenungId).orElse(null);
        gangneung.updateCreateTime(LocalDateTime.of(2022,3,1,13,00));

        Long gyungjuId = citySetUp.getCityId(memberId, "경주");
        City gyungju = cityRepository.findById(gyungjuId).orElse(null);
        gyungju.updateCreateTime(LocalDateTime.of(2022,2,25,13,00));

        Long ulsanId = citySetUp.getCityId(memberId, "울산");
        City ulsan = cityRepository.findById(ulsanId).orElse(null);
        ulsan.updateCreateTime(LocalDateTime.of(2022,1,2,13,00));

        //when & then
        mvc.perform(get("/city")
                        .header("member-id", memberId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.cities[0].name").value("서울"))
                .andExpect(jsonPath("$.data.cities[1].name").value("전주"))
                .andExpect(jsonPath("$.data.cities[2].name").value("서울"))
                .andExpect(jsonPath("$.data.cities[3].name").value("대구"))
                .andExpect(jsonPath("$.data.cities[4].name").value("광주"))
                .andExpect(jsonPath("$.data.cities[5].name").value("인천"))
                .andExpect(jsonPath("$.data.cities[6].name").value("대전"))
                .andExpect(jsonPath("$.data.cities[7].name").value("제주"))
                .andExpect(jsonPath("$.data.cities[8].name").value("강릉"))
                .andExpect(jsonPath("$.data.cities[9].name").value("경주"));
    }

    @Test
    @DisplayName("여행 중인 도시가 10개 이상(중복 포함)인 경우 모두 노출하기에 성공한다.")
    void get_traveling_cities_more_than_10_test() throws Exception {
        //given
        Long memberId = memberSetUp.getMemberId("인터파크");

        Long seoulId = citySetUp.getCityId(memberId, "서울");
        Long jeonjuId = citySetUp.getCityId(memberId, "전주");
        Long gangnengId = citySetUp.getCityId(memberId, "강릉");
        Long gyungjuId = citySetUp.getCityId(memberId, "경주");
        Long ulsanId = citySetUp.getCityId(memberId, "울산");
        Long jejuId = citySetUp.getCityId(memberId, "제주");

        travelSetUp.getTravelId(memberId, seoulId, LocalDate.of(2023,2,1), LocalDate.of(2323, 4, 10));
        travelSetUp.getTravelId(memberId, jeonjuId, LocalDate.of(2023,2,20), LocalDate.of(2323, 4, 10));
        travelSetUp.getTravelId(memberId, gangnengId, LocalDate.of(2023,3,1), LocalDate.of(2323, 4, 10));
        travelSetUp.getTravelId(memberId, gyungjuId, LocalDate.of(2023,3,2), LocalDate.of(2323, 4, 10));
        travelSetUp.getTravelId(memberId, seoulId, LocalDate.of(2023,3,3), LocalDate.of(2323, 4, 10));
        travelSetUp.getTravelId(memberId, ulsanId, LocalDate.of(2023,3,4), LocalDate.of(2323, 4, 10));
        travelSetUp.getTravelId(memberId, jejuId, LocalDate.of(2023,3,5), LocalDate.of(2323, 4, 10));
        travelSetUp.getTravelId(memberId, seoulId, LocalDate.of(2023,3,6), LocalDate.of(2323, 4, 10));
        travelSetUp.getTravelId(memberId, jeonjuId, LocalDate.of(2023,3,7), LocalDate.of(2323, 4, 10));
        travelSetUp.getTravelId(memberId, seoulId, LocalDate.of(2023,3,10), LocalDate.of(2323, 4, 10));
        travelSetUp.getTravelId(memberId, ulsanId, LocalDate.of(2023,3,15), LocalDate.of(2323, 4, 10));
        travelSetUp.getTravelId(memberId, jejuId, LocalDate.of(2023,3,20), LocalDate.of(2323, 4, 10));

        //when & then
        mvc.perform(get("/city")
                        .header("member-id", memberId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.cities[0].name").value("서울"))
                .andExpect(jsonPath("$.data.cities[1].name").value("전주"))
                .andExpect(jsonPath("$.data.cities[2].name").value("강릉"))
                .andExpect(jsonPath("$.data.cities[3].name").value("경주"))
                .andExpect(jsonPath("$.data.cities[4].name").value("서울"))
                .andExpect(jsonPath("$.data.cities[5].name").value("울산"))
                .andExpect(jsonPath("$.data.cities[6].name").value("제주"))
                .andExpect(jsonPath("$.data.cities[7].name").value("서울"))
                .andExpect(jsonPath("$.data.cities[8].name").value("전주"))
                .andExpect(jsonPath("$.data.cities[9].name").value("서울"))
                .andExpect(jsonPath("$.data.cities[10].name").value("울산"))
                .andExpect(jsonPath("$.data.cities[11].name").value("제주"));
    }
}
