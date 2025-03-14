package com.mohamed.fullTestingDemo.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequestDto(
        @NotBlank
        String username,

        @NotBlank
        @Email
        String email
){
}
