package com.interpark.assignment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private LocalDate startDate;

    private LocalDate endDate;

    @Builder
    public Travel(Member member, City city, LocalDate startDate, LocalDate endDate) {
        this.member = member;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateDate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateCity(City city) {
        if (this.city != null) {
            this.city.getTravels().remove(this);
        }
        this.city = city;
        city.getTravels().add(this);
    }

    public void removeCity() {
        if (this.city != null) {
            this.city.getTravels().remove(this);
        }
        this.city = null;
    }
}
