package com.interpark.assignment.integration;

import com.interpark.assignment.dto.member.MemberCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerTest extends BaseIntegrationTest {

    @Test
    @DisplayName("멤버를 생성한다.")
    void create_member_test() throws Exception {
        //given
        MemberCreateDto request = MemberCreateDto.builder()
                .name("인터파크")
                .build();

        //when
        ResultActions result = mvc.perform(post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                )
                .andDo(print());

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists());
    }
}
