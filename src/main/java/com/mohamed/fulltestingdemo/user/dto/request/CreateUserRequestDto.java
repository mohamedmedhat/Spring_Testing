package com.mohamed.fulltestingdemo.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@Builder
public record CreateUserRequestDto (
        @NotBlank
        String username,

        @NotBlank
        @Email
        String email
){
        public static CreateUserRequestDto sanitize(CreateUserRequestDto dto){
                return new CreateUserRequestDto(
                        Jsoup.clean(dto.username, Safelist.none()),
                        Jsoup.clean(dto.email, Safelist.none())
                );
        }
}
