package com.mohamed.fulltestingdemo.user;

import com.mohamed.fulltestingdemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fulltestingdemo.user.dto.request.UpdateUserRequestDto;
import com.mohamed.fulltestingdemo.user.dto.response.CreateUserResponseDto;
import com.mohamed.fulltestingdemo.user.dto.response.GetUsersResponseDto;
import org.owasp.encoder.Encode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public User toEntity(CreateUserRequestDto data) {
        CreateUserRequestDto sanitizedDto = CreateUserRequestDto.sanitize(data);
        User user = new User();
        user.setUsername(sanitizedDto.username());
        user.setEmail(sanitizedDto.email());
        return user;
    }

    public CreateUserResponseDto toCreateDto(User user) {
        return new CreateUserResponseDto(
                user.getId(),
                Encode.forHtml(user.getUsername()),
                Encode.forHtml( user.getEmail())
        );
    }

    public List<GetUsersResponseDto> toGetAllDto(List<User> users) {
        return users.stream()
                .map(user ->
                        new GetUsersResponseDto(
                                user.getId(),
                                Encode.forHtml(user.getUsername()),
                                Encode.forHtml( user.getEmail())
                        )
                )
                .toList();
    }

    public GetUsersResponseDto toGetDto(User user) {
        return new GetUsersResponseDto(
                user.getId(),
                Encode.forHtml(user.getUsername()),
                Encode.forHtml( user.getEmail())
        );
    }

    public User toUpdateEntity(User user, UpdateUserRequestDto data) {
        UpdateUserRequestDto sanitizedDto = UpdateUserRequestDto.sanitize(data);
        user.setEmail(sanitizedDto.email());
        user.setUsername(sanitizedDto.username());
        return user;
    }
}
