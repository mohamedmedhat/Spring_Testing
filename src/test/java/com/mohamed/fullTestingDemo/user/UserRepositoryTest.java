package com.mohamed.fullTestingDemo.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import com.mohamed.fullTestingDemo.TestcontainersConfiguration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Transactional
@Import(TestcontainersConfiguration.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("mohamed")
                .email("mmr944@gmail.com")
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    @Test
    public void findByUserName_ShouldReturnUser() {
        // When
        Optional<User> foundUser = userRepository.findByUsername("mohamed");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("mohamed");
        assertThat(foundUser.get().getEmail()).isEqualTo("mmr944@gmail.com");
    }

    @Test
    public void findByUserName_ShouldReturnEmptyIfUserNotFound() {
        // When
        Optional<User> foundUser = userRepository.findByUsername("nonexistent");

        // Then
        assertThat(foundUser).isEmpty();
    }
}
