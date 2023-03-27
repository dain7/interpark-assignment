package com.interpark.assignment.repository;

import com.interpark.assignment.domain.Travel;
import com.interpark.assignment.repository.custom.TravelRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Long>, TravelRepositoryCustom {
}
