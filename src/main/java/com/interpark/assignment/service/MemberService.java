package com.interpark.assignment.service;

import com.interpark.assignment.domain.Member;
import com.interpark.assignment.dto.member.MemberCreateDto;
import com.interpark.assignment.dto.member.MemberResponseDto;
import com.interpark.assignment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto signup(MemberCreateDto request) {
        Member newMember = Member.builder()
                .name(request.getName())
                .build();
        memberRepository.save(newMember);
        return MemberResponseDto.builder()
                .id(newMember.getId())
                .name(newMember.getName())
                .build();
    }
}
