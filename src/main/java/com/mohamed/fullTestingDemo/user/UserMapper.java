package com.mohamed.fullTestingDemo.user;

import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.request.UpdateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import com.mohamed.fullTestingDemo.user.dto.response.GetUsersResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User toEntity(CreateUserRequestDto data) {
        User user = new User();
        user.setUsername(data.username());
        user.setEmail(data.email());
        return user;
    }

    public CreateUserResponseDto toCreateDto(User user) {
        return new CreateUserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public List<GetUsersResponseDto> toGetAllDto(List<User> users) {
        return users.stream()
                .map(user ->
                        new GetUsersResponseDto(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail()
                        )
                )
                .collect(Collectors.toList());
    }

    public GetUsersResponseDto toGetDto(User user) {
        return new GetUsersResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public User toUpdateEntity(User user, UpdateUserRequestDto data) {
        user.setEmail(data.email());
        user.setUsername(data.username());
        return user;
    }
}
