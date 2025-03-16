package com.mohamed.fulltestingdemo.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public record UpdateUserRequestDto(
        @NotBlank
        String username,

        @NotBlank
        @Email
        String email
){
        public static UpdateUserRequestDto sanitize(UpdateUserRequestDto dto){
                return new UpdateUserRequestDto(
                        Jsoup.clean(dto.username, Safelist.none()),
                        Jsoup.clean(dto.email, Safelist.none())
                );
        }
}
