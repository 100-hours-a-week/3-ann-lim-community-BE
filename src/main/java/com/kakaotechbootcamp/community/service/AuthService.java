package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.user.request.UserLoginRequestDto;
import com.kakaotechbootcamp.community.dto.user.response.UserLoginResponseDto;
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
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserLoginResponseDto login(UserLoginRequestDto loginRequest) {

        User user = userRepository.findByEmailAndDeletedAtIsNull(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        return new UserLoginResponseDto(user.getId());
    }

    //Todo: JWT 구현 후 수정
    public void logout() {
    }
}
