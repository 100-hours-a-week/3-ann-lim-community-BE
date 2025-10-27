package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.user.request.CreateUserRequestDto;
import com.kakaotechbootcamp.community.dto.user.request.UpdateUserPasswordRequestDto;
import com.kakaotechbootcamp.community.dto.user.request.UpdateUserRequestDto;
import com.kakaotechbootcamp.community.dto.user.response.CreateUserResponseDto;
import com.kakaotechbootcamp.community.dto.user.response.EditUserResponseDto;
import com.kakaotechbootcamp.community.dto.user.response.UpdateUserResponseDto;
import com.kakaotechbootcamp.community.dto.user.response.UserProfileResponseDto;
import com.kakaotechbootcamp.community.entity.User;
import com.kakaotechbootcamp.community.exception.CustomException;
import com.kakaotechbootcamp.community.exception.ErrorCode;
import com.kakaotechbootcamp.community.repository.*;
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
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

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

    @Transactional(readOnly = true)
    public EditUserResponseDto getUserForEdit(Long userId) {

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return EditUserResponseDto.of(user.getId(), user.getEmail(), user.getNickname(), user.getProfileImage());
    }

    public UpdateUserResponseDto updateUserInfo(Long userId, UpdateUserRequestDto updateUserRequest) {

        if (updateUserRequest == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST_BODY);
        }

        if (updateUserRequest.isAllFieldsNull()) {
            throw new CustomException(ErrorCode.NO_UPDATE_CONTENT);
        }

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (userRepository.existsByNicknameAndDeletedAtIsNull(updateUserRequest.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        user.updateUserInfo(updateUserRequest.getNickname(), updateUserRequest.getProfileImage());

        return new UpdateUserResponseDto(user.getId());
    }

    //Todo: JWT 구현 후 수정
    public void updateUserPassword(Long userId, UpdateUserPasswordRequestDto updateUserPasswordRequest) {

        if (!updateUserPasswordRequest.getPassword().equals(updateUserPasswordRequest.getPasswordConfirm())) {
            throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
        }

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.updateUserPassword(passwordEncoder.encode(updateUserPasswordRequest.getPassword()));
    }

    //Todo: JWT 구현 후 수정
    public void deleteUser(Long userId) {

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.delete();
    }
}
