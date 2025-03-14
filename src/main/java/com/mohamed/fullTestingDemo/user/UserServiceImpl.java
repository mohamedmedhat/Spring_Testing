package com.mohamed.fullTestingDemo.user;

import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.request.UpdateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import com.mohamed.fullTestingDemo.user.dto.response.GetUsersResponseDto;
import com.mohamed.fullTestingDemo.user.exceptions.UserAlreadyExistsException;
import com.mohamed.fullTestingDemo.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto data) {
        if (userRepository.findByUsername(data.username()).isPresent()) {
            throw new UserAlreadyExistsException("User with username '" + data.username() + "' already exists");
        }
        User user = this.userMapper.toEntity(data);
        User savedUser = this.userRepository.save(user);
        return this.userMapper.toCreateDto(savedUser);
    }

    @Async("taskExecutor")
    @Override
    public CompletableFuture<List<GetUsersResponseDto>> getAllUsers(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> userPage = this.userRepository.findAll(pageable);
        return CompletableFuture.supplyAsync(() -> userMapper.toGetAllDto(userPage.getContent()));
    }

    public User findUserById(Long id){
       return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user with id: "+id+" not found"));
    }

    @Override
    public GetUsersResponseDto getUserById(Long id) {
        User user = this.findUserById(id);
        return this.userMapper.toGetDto(user);
    }

    @Override
    public GetUsersResponseDto updateUser(Long id, UpdateUserRequestDto data) {
         User user = this.findUserById(id);
         User updatedUser = this.userMapper.toUpdateEntity(user, data);
         User savedUpdatedUser = this.userRepository.save(updatedUser);
         return this.userMapper.toGetDto(savedUpdatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = this.findUserById(id);
        userRepository.delete(user);
    }
}
