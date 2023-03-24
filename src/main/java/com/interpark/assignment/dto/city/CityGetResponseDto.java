package com.interpark.assignment.dto.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class CityGetResponseDto {
    private Long id;
    private String name;
}
