package com.mohamed.fullTestingDemo.user;

import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import com.mohamed.fullTestingDemo.user.dto.response.GetAllUsersResponseDto;
import com.mohamed.fullTestingDemo.user.exceptions.UserAlreadyExistsException;
import jakarta.validation.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // ✅ No @SpringBootTest for unit tests
class UserServiceTest { // ? TDD => Red, Green, Refactor

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    void testCreateUser_shouldSaveUserReturnCreateResponseDto() {
        // Given  (Arrange)
        CreateUserRequestDto requestDto = new CreateUserRequestDto("mohamed_medhat", "mmr973320@example.com");

        User user = new User();
        user.setUsername(requestDto.username());
        user.setEmail(requestDto.email());

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("mohamed_medhat");
        savedUser.setEmail("mmr973320@example.com");

        CreateUserResponseDto responseDto = new CreateUserResponseDto(1L, "mohamed_medhat", "mmr973320@example.com");

        given(userMapper.toEntity(requestDto)).willReturn(user);
        given(userRepository.save(user)).willReturn(savedUser);
        given(userMapper.toCreateDto(savedUser)).willReturn(responseDto);

        // When (Act)
        CreateUserResponseDto response = this.userService.createUser(requestDto);

        // Then (Assert)
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.username()).isEqualTo("mohamed_medhat");
        assertThat(response.email()).isEqualTo("mmr973320@example.com");

        verify(userMapper).toEntity(requestDto);
        verify(userRepository).save(user);
        verify(userMapper).toCreateDto(savedUser);
    }

    @Test
    void testCreateUser_shouldThrowExceptionWhenUsernameIsBlank() {
        // Given
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateUserRequestDto requestDto = new CreateUserRequestDto("", "john@example.com");

        // When & Then
        Set<ConstraintViolation<CreateUserRequestDto>> violations = validator.validate(requestDto);

        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("must not be blank");
    }


    @Test
    void testCreateUser_shouldThrowExceptionWhenUserAlreadyExists() {
        // Given
        CreateUserRequestDto requestDto = new CreateUserRequestDto("mohamed_medhat", "mmr973320@example.com");
        User existingUser = new User();
        existingUser.setUsername("mohamed_medhat");
        existingUser.setEmail("mmr973320@example.com");

        given(userRepository.findByUsername("mohamed_medhat")).willReturn(Optional.of(existingUser));

        // When & Then
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(requestDto);
        });

        assertThat(exception.getMessage()).isEqualTo("User with username 'mohamed_medhat' already exists");

        verify(userRepository).findByUsername("mohamed_medhat");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testGetAllUsers_shouldReturnListOfAllUsers(){
        // Given (Arrange)
        int page = 0, size= 10;
        Pageable pageable = PageRequest.of(page, size);

        User user1 = User.builder().id(1L).email("user1@gamil.com").username("user1").createdAt(LocalDateTime.now()).build();
        User user2 = User.builder().id(2L).email("user2@gamil.com").username("user2").createdAt(LocalDateTime.now()).build();
        List<User> users = List.of(user1, user2);

        Page<User> userPage = new PageImpl<>(users, pageable, users.size());

        given(userRepository.findAll(pageable)).willReturn(userPage);

        GetAllUsersResponseDto dto1 = new GetAllUsersResponseDto(1L, "user1", "user1@gmail.com");
        GetAllUsersResponseDto dto2 = new GetAllUsersResponseDto(2L, "user2", "user2@gmail.com");

        given(userMapper.toGetAllDto(users)).willReturn(List.of(dto1, dto2));

        // When (Act)
        CompletableFuture<List<GetAllUsersResponseDto>> futureResult = userService.getAllUsers(page, size);
        List<GetAllUsersResponseDto> result = futureResult.join();

        // Then (Assert)
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).username()).isEqualTo("user1");
        assertThat(result.get(1).username()).isEqualTo("user2");

        verify(userRepository, times(1)).findAll(pageable);
        verify(userMapper, times(1)).toGetAllDto(users);    }
}
