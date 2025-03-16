package com.mohamed.fulltestingdemo.bdd.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamed.fulltestingdemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fulltestingdemo.user.dto.response.CreateUserResponseDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 public class UserStepDefinition {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateUserResponseDto responseDto;
    private Exception exception;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Given("a user with username {string} and email {string}")
    public void givenAUser(String username, String email) throws Exception {
        CreateUserRequestDto requestDto = new CreateUserRequestDto(username, email);

        String responseJson = mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        responseDto = objectMapper.readValue(responseJson, CreateUserResponseDto.class);
    }

    @Given("a user with username {string} and email {string} already exists")
    public void givenExistingUser(String username, String email) throws Exception {
        CreateUserRequestDto requestDto = new CreateUserRequestDto(username, email);
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @When("the user is registered")
    public void whenUserIsRegistered() {
        try {
            CreateUserRequestDto requestDto = new CreateUserRequestDto(responseDto.username(), responseDto.email());
            responseDto = new CreateUserResponseDto(1L, requestDto.username(), requestDto.email());
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
