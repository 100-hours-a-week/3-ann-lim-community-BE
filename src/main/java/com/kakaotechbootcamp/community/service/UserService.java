package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.user.request.CreateUserRequestDto;
import com.kakaotechbootcamp.community.dto.user.response.CreateUserResponseDto;
import com.kakaotechbootcamp.community.entity.User;
import com.kakaotechbootcamp.community.exception.CustomException;
import com.kakaotechbootcamp.community.exception.ErrorCode;
import com.kakaotechbootcamp.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserResponseDto signup(CreateUserRequestDto createUserRequest) {
        
        if(userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        if(userRepository.existsByNickname(createUserRequest.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        if(!createUserRequest.getPassword().equals(createUserRequest.getPasswordConfirm())) {
            throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
        }

        User user = new User(createUserRequest.getEmail(),
                passwordEncoder.encode(createUserRequest.getPassword()),
                createUserRequest.getNickname(), createUserRequest.getProfileImage());
        User savedUser = userRepository.save(user);

        return new CreateUserResponseDto(savedUser.getId());
    }

}
