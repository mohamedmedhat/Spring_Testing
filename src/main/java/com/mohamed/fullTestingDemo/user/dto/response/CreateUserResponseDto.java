package com.mohamed.fullTestingDemo.user.dto.response;

public record CreateUserResponseDto(
        Long id,
        String username,
        String email
) {
}
