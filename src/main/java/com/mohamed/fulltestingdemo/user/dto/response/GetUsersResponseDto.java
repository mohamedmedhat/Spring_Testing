package com.mohamed.fulltestingdemo.user.dto.response;

public record GetUsersResponseDto(
        Long id,
        String username,
        String email
) {
}
