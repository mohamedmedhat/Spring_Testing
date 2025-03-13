package com.mohamed.fullTestingDemo.user;


import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
