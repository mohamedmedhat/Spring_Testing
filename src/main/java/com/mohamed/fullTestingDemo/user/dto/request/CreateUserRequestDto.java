package com.mohamed.fullTestingDemo.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateUserRequestDto (
        @NotBlank
        String username,

        @NotBlank
        @Email
        String email
){
}
