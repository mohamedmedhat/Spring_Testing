package com.mohamed.fullTestingDemo.user.dto.response;

public record GetAllUsersResponseDto(
        Long id,
        String username,
        String email
) {
}
