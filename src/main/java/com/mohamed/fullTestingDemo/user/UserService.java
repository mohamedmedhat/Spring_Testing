package com.mohamed.fullTestingDemo.user;

import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import com.mohamed.fullTestingDemo.user.dto.response.GetAllUsersResponseDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
  CreateUserResponseDto createUser(CreateUserRequestDto data);
  CompletableFuture<List<GetAllUsersResponseDto>> getAllUsers(int page, int pageSize);
}
