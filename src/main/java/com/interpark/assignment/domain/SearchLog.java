package com.interpark.assignment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SearchLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long memberId;

    private Long cityId;

    @CreatedDate
//    @Column(updatable = false)
    private LocalDateTime createDatetime;

    @Builder
    public SearchLog(Long memberId, Long cityId) {
        this.memberId = memberId;
        this.cityId = cityId;
    }

    public void updateTime(LocalDateTime localDateTime) {
        this.createDatetime = localDateTime;
    }

}
