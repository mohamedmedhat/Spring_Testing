package com.mohamed.fullTestingDemo.user;

import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import com.mohamed.fullTestingDemo.user.dto.response.GetAllUsersResponseDto;
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

    public List<GetAllUsersResponseDto> toGetAllDto(List<User> users) {
        return users.stream()
                .map(user ->
                        new GetAllUsersResponseDto(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail()
                        )
                )
                .collect(Collectors.toList());
    }
}
