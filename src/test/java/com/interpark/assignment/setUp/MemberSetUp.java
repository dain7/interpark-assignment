package com.interpark.assignment.setUp;

import com.interpark.assignment.domain.Member;
import com.interpark.assignment.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberSetUp {
    @Autowired
    private MemberRepository memberRepository;

    public Long getMemberId(String name) {
        Member member = Member.builder()
                .name(name)
                .build();
        return memberRepository.save(member).getId();
    }
}
