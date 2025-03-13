package com.mohamed.fullTestingDemo.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static reactor.core.publisher.Mono.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest { // ? TDD => Red, Green, Refactor
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    void testCreateUser_shouldSaveUerReturnCreateResponseDto(){
        //Arrange
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto("medhat", "mmr973320@gmail.com");

        when(userRepository.save()).thenAnswer(inv -> {
            User user = inv.getArgument(0);
            user.setId(0);
            return user;
        });
        //Act
        CreateUserResponseDto res = userService.createUser(createUserRequestDto);
        //Assert
        Assertions.assertNotNull(res);
        Assertions.assertEquals("medhat", res.username);
    }
}
