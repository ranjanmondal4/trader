package com.trader.service.user;

import com.trader.dto.user.LoginReceiveDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testing User Service Test")
class UserServiceTest {

    @InjectMocks
    UserService userService;

    /*@BeforeEach
    void init_mocks() {
        MockitoAnnotations.initMocks(this);
    }*/

    @Test
    void login() {
        LoginReceiveDTO loginDTO = new LoginReceiveDTO();
        loginDTO.setEmail("ranjan.mondal@oodlestechnologies.com");
        loginDTO.setPassword("ranjan@oodles1");
        assertEquals(userService.login(loginDTO).isSuccess(), true);
    }

    /*@Test
    void getByUserId() {
    }*/
}