package com.interpark.assignment.dto.travel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelResponseDto {
    private Long id;
    private String cityName;
    private LocalDate startDate;
    private LocalDate endDate;
}
