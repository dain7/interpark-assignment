package com.interpark.assignment.repository;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.repository.custom.CityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long>, CityRepositoryCustom {

}
