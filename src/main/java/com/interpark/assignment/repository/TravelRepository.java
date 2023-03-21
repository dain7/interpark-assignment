package com.interpark.assignment.repository;

import com.interpark.assignment.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Long> {
}
