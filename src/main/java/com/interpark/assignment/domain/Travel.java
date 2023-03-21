package com.interpark.assignment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    private Timestamp startDate;

    private Timestamp endDate;

    @Builder
    public Travel(City city, Timestamp startDate, Timestamp endDate) {
        this.city = city;
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
}
