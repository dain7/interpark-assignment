package com.interpark.assignment.repository;

import com.interpark.assignment.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

}
