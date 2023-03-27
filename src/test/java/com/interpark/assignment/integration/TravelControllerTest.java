package com.interpark.assignment.integration;

import com.interpark.assignment.domain.Travel;
import com.interpark.assignment.dto.travel.TravelRequestDto;
import com.interpark.assignment.repository.TravelRepository;
import com.interpark.assignment.setUp.CitySetUp;
import com.interpark.assignment.setUp.MemberSetUp;
import com.interpark.assignment.setUp.TravelSetUp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TravelControllerTest extends BaseIntegrationTest{
    @Autowired
    private MemberSetUp memberSetUp;
    @Autowired
    private CitySetUp citySetUp;
    @Autowired
    private TravelSetUp travelSetUp;
    @Autowired
    private TravelRepository travelRepository;

    @Test
    @DisplayName("여행 등록에 성공한다.")
    void create_travel_test() throws Exception {
        //given
        Long memberId = memberSetUp.getMemberId("인터파크");
        Long cityId = citySetUp.getCityId(memberId, "서울");

        TravelRequestDto request = TravelRequestDto.builder()
                .cityId(cityId)
                .startDate(LocalDate.of(2023, 3, 1))
                .endDate(LocalDate.of(2023, 3, 5))
                .build();

        //when
        mvc.perform(post("/travel")
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
    @DisplayName("여행 수정에 성공한다.")
    void update_travel_test() throws Exception {
        //given
        Long memberId = memberSetUp.getMemberId("인터파크");
        Long cityId = citySetUp.getCityId(memberId, "서울");
        Long travelId = travelSetUp.getTravelId(memberId, cityId, LocalDate.of(2023, 3, 1), LocalDate.of(2023, 3, 10));

        Long updateCityId = citySetUp.getCityId(memberId, "부산");

        TravelRequestDto request = TravelRequestDto.builder()
                .cityId(updateCityId)
                .startDate(LocalDate.of(2023, 4, 5))
                .endDate(LocalDate.of(2023, 5, 20))
                .build();

        //when
        mvc.perform(post("/travel/{travelId}", travelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                )
                .andDo(print())
                .andExpect(status().isOk());

        // then
        Travel travel = travelRepository.findById(travelId).orElse(null);
        assert travel != null;
        Assertions.assertEquals("부산", travel.getCity().getName());
        Assertions.assertEquals(LocalDate.of(2023, 4, 5), travel.getStartDate());
        Assertions.assertEquals(LocalDate.of(2023, 5, 20), travel.getEndDate());
    }

    @Test
    @DisplayName("여행 삭제에 성공한다.")
    void delete_travel_test() throws Exception {
        //given
        Long memberId = memberSetUp.getMemberId("인터파크");
        Long cityId = citySetUp.getCityId(memberId, "서울");
        Long travelId = travelSetUp.getTravelId(memberId, cityId, LocalDate.of(2023, 3, 1), LocalDate.of(2023, 3, 10));

        //when
        mvc.perform(delete("/travel/{travelId}", travelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        // then
        Travel travel = travelRepository.findById(travelId).orElse(null);
        Assertions.assertNull(travel);
    }

    @Test
    @DisplayName("단일 여행 조회에 성공한다.")
    void get_travel_test() throws Exception {
        //given
        Long memberId = memberSetUp.getMemberId("인터파크");
        Long cityId = citySetUp.getCityId(memberId, "서울");
        Long travelId = travelSetUp.getTravelId(memberId, cityId, LocalDate.of(2023, 3, 1), LocalDate.of(2023, 3, 10));

        //when
        mvc.perform(get("/travel/{travelId}", travelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.travel.id").exists())
                .andExpect(jsonPath("$.data.travel.cityName").value("서울"))
                .andExpect(jsonPath("$.data.travel.startDate").value("2023-03-01"))
                .andExpect(jsonPath("$.data.travel.endDate").value("2023-03-10"));
    }
}
