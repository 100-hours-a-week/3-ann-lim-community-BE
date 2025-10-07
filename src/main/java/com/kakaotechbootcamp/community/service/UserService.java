package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.user.request.CreateUserRequestDto;
import com.kakaotechbootcamp.community.dto.user.response.CreateUserResponseDto;
import com.kakaotechbootcamp.community.dto.user.response.UserProfileResponseDto;
import com.kakaotechbootcamp.community.entity.User;
import com.kakaotechbootcamp.community.exception.CustomException;
import com.kakaotechbootcamp.community.exception.ErrorCode;
import com.kakaotechbootcamp.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserProfileResponseDto getProfileImage() {

        //Todo: JWT 구현 후 수정
        User user = userRepository.findByIdAndDeletedAtIsNull(1L)
                .orElseThrow();

        return new UserProfileResponseDto(user.getProfileImage());
    }

    public CreateUserResponseDto signup(CreateUserRequestDto createUserRequest) {
        
        if (userRepository.existsByEmailAndDeletedAtIsNull(createUserRequest.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        if (userRepository.existsByNicknameAndDeletedAtIsNull(createUserRequest.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        if (!createUserRequest.getPassword().equals(createUserRequest.getPasswordConfirm())) {
            throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
        }

        User user = new User(createUserRequest.getEmail(),
                passwordEncoder.encode(createUserRequest.getPassword()),
                createUserRequest.getNickname(), createUserRequest.getProfileImage());
        userRepository.save(user);

        return new CreateUserResponseDto(user.getId());
    }

}
