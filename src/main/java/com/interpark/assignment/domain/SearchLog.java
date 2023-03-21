package com.interpark.assignment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long cityId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDatetime;

    @Builder
    public SearchLog(Long userId, Long cityId) {
        this.userId = userId;
        this.cityId = cityId;
    }
}
