package com.mohamed.fullTestingDemo.user.dto.response;

public record GetUsersResponseDto(
        Long id,
        String username,
        String email
) {
}
