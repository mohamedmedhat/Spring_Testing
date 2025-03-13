package com.mohamed.fullTestingDemo.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    void testAddUser_ShouldReturn201AndUserResponse() throws Exception {
        // Given
        CreateUserRequestDto requestDto = new CreateUserRequestDto("mohamed_medhat", "mmr973320@example.com");
        CreateUserResponseDto responseDto = new CreateUserResponseDto(1L, "mohamed_medhat", "mmr973320@example.com");

        given(userService.createUser(Mockito.any(CreateUserRequestDto.class))).willReturn(responseDto);

        // When & Then
        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("mohamed_medhat"))
                .andExpect(jsonPath("$.email").value("mmr973320@example.com"));
    }
}