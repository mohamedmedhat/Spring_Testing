package com.mohamed.fullTestingDemo.user;


import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.request.UpdateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import com.mohamed.fullTestingDemo.user.dto.response.GetUsersResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserResponseDto> addUser(@Valid @RequestBody CreateUserRequestDto data) {
        CreateUserResponseDto response = userService.createUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<GetUsersResponseDto>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getAllUsers(page, size)
                .thenApply(users -> ResponseEntity.ok().body(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUsersResponseDto> getUserById(@PathVariable("id") Long id) {
        GetUsersResponseDto response = this.userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<GetUsersResponseDto> updateUser(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateUserRequestDto data
    ) {
        GetUsersResponseDto response = this.userService.updateUser(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
