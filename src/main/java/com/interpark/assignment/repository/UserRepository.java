package com.interpark.assignment.repository;

import com.interpark.assignment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
