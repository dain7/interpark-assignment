package com.interpark.assignment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String name;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDatetime;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval=true)
    private final List<Travel> travels = new ArrayList<>();

    @Builder
    public City(User user, String name) {
        this.user = user;
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
