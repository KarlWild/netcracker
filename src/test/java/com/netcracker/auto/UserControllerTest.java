package com.netcracker.auto;

import com.netcracker.auto.web.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Anton Popkov
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("an@mail.ru")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Test
    public void authenticatedPageTest() throws Exception {
        this.mockMvc.perform(get("/lk/all"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(content().string(containsString("Popkov Anton")));

    }
}
