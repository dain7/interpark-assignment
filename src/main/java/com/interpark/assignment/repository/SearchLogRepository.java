package com.interpark.assignment.repository;

import com.interpark.assignment.domain.SearchLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchLogRepository extends JpaRepository<SearchLog, Long> {
}
