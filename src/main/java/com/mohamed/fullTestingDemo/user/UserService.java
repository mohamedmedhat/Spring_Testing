package com.mohamed.fullTestingDemo.user;

import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.request.UpdateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import com.mohamed.fullTestingDemo.user.dto.response.GetUsersResponseDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
  CreateUserResponseDto createUser(CreateUserRequestDto data);
  CompletableFuture<List<GetUsersResponseDto>> getAllUsers(int page, int pageSize);
  GetUsersResponseDto getUserById(Long id);
  GetUsersResponseDto updateUser(Long id, UpdateUserRequestDto data);
  void deleteUser(Long id);
}
