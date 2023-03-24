package com.interpark.assignment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EntityListeners(AuditingEntityListener.class)
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String name;

    @CreatedDate
    private LocalDateTime createDatetime;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval=true)
    private final List<Travel> travels = new ArrayList<>();

    @Builder
    public City(Member member, String name) {
        this.member = member;
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateCreateTime(LocalDateTime localDateTime) {
        this.createDatetime = localDateTime;
    }
}
