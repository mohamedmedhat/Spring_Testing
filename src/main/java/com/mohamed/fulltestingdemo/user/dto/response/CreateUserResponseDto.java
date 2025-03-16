package com.mohamed.fulltestingdemo.user.dto.response;

import lombok.Builder;

@Builder
public record CreateUserResponseDto(
        Long id,
        String username,
        String email
) {
}
