package com.mohamed.fullTestingDemo.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamed.fullTestingDemo.user.dto.request.CreateUserRequestDto;
import com.mohamed.fullTestingDemo.user.dto.response.CreateUserResponseDto;
import com.mohamed.fullTestingDemo.user.dto.response.GetUsersResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private CreateUserRequestDto requestDto;
    private CreateUserResponseDto responseDto;
    private List<GetUsersResponseDto> usersList;

    @BeforeEach
    public void setUp(){
        requestDto = CreateUserRequestDto.builder().username("mohamed_medhat").email("mmr973320@example.com").build();
        responseDto = CreateUserResponseDto.builder().id(1L).username("mohamed_medhat").email("mmr973320@example.com").build();
        usersList = List.of(
                new GetUsersResponseDto(1L, "user1", "user1@example.com"),
                new GetUsersResponseDto(2L, "user2", "user2@example.com")
        );
    }

    @Test
    void testAddUser_ShouldReturn201AndUserResponse() throws Exception {

        given(userService.createUser(any(CreateUserRequestDto.class))).willReturn(responseDto);
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

    @Test
    void testGetAllUsers_ShouldReturn200AndListOfUsers() throws Exception {
        // Given: Mock user list
        List<GetUsersResponseDto> usersList = List.of(
                new GetUsersResponseDto(1L, "user1", "user1@example.com"),
                new GetUsersResponseDto(2L, "user2", "user2@example.com")
        );

        // Mock service to return CompletableFuture<List<GetAllUsersResponseDto>>
        given(userService.getAllUsers(anyInt(), anyInt()))
                .willReturn(CompletableFuture.completedFuture(usersList));

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].email").value("user2@example.com"));
    }
}