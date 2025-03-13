import com.mohamed.fullTestingDemo.user.User;
import com.mohamed.fullTestingDemo.user.UserRepository;
import com.mohamed.fullTestingDemo.user.UserService;
import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserStepdefs {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private CreateUserResponseDto responseDto;
    private Exception exception;


    @Before
    public void resetDatabase() {
        userRepository.deleteAll();
    }

    @Given("a user with username {string} and email {string}")
    public void givenAUser(String username, String email) {
        CreateUserRequestDto requestDto = new CreateUserRequestDto(username, email);
        responseDto = userService.createUser(requestDto);
    }

    @Given("a user with username {string} and email {string} already exists")
    public void givenExistingUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        userRepository.save(user);
    }

    @When("the user is registered")
    public void whenUserIsRegistered() {
        try {
            CreateUserRequestDto requestDto = new CreateUserRequestDto(responseDto.username(), responseDto.email());
            responseDto = userService.createUser(requestDto);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @Then("the response should contain the username {string}")
    public void thenResponseShouldContainUsername(String expectedUsername) {
        assertThat(responseDto.username()).isEqualTo(expectedUsername);
    }

    @Then("an error {string} should be returned")
    public void thenAnErrorShouldBeReturned(String expectedMessage) {
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }
}

