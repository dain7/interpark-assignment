package com.interpark.assignment.service;

import com.interpark.assignment.domain.User;
import com.interpark.assignment.dto.user.UserCreateDto;
import com.interpark.assignment.dto.user.UserResponseDto;
import com.interpark.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto signup(UserCreateDto request) {
        User newUser = User.builder()
                .name(request.getName())
                .build();
        userRepository.save(newUser);
        return UserResponseDto.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .build();
    }
}
