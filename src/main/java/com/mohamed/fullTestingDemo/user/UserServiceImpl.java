package com.mohamed.fullTestingDemo.user;

import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto data) {
        User user = this.userMapper.toEntity(data);
        User savedUser = this.userRepository.save(user);
        return this.userMapper.toCreateDto(savedUser);
    }
}
