package com.mohamed.fullTestingDemo.user;

import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import com.mohamed.fullTestingDemo.user.exceptions.UserAlreadyExistsException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class) // ✅ No @SpringBootTest for unit tests
class UserServiceTest {

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
        CreateUserRequestDto requestDto = new CreateUserRequestDto("", "john@example.com");

        // When & Then
        assertThrows(ConstraintViolationException.class, () -> userService.createUser(requestDto));
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
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
}
