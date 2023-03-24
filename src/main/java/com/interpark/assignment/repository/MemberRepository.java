package com.interpark.assignment.repository;

import com.interpark.assignment.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
