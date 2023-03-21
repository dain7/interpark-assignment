package com.interpark.assignment;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AssignmentApplicationTests {

	private final CityRepository cityRepository;

	@Autowired
	AssignmentApplicationTests(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@Test
	@DisplayName("JPA 동작 테스트")
	void JPA_Test() {
		City city = City.builder()
				.name("도시")
				.build();
		cityRepository.save(city);

		City findCity = cityRepository.findById(city.getId()).get();
		Assertions.assertEquals(city.getName(), findCity.getName());
	}

}
