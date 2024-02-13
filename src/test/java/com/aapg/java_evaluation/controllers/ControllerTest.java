package com.aapg.java_evaluation.controllers;

import com.aapg.java_evaluation.controller.UserController;
import com.aapg.java_evaluation.model.dto.PhoneDTO;
import com.aapg.java_evaluation.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(
        exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
}
)
public class ControllerTest {

    @MockBean
    private UserController userControllerMock;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        List<PhoneDTO> phones = new ArrayList<>();
        UserDTO user = null;
        PhoneDTO phone = null;

        phones.add(phone.builder()
                .number("12345678")
                .cityCode("1")
                .countryCode("57")
                .build());

        Mockito.when(userControllerMock.create(UserDTO.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("1234")
                .phones(phones)
                .build()).getStatusCode());

        Mockito.when(userControllerMock.create(UserDTO.builder().build()).getStatusCode());
    }

    @Test
    public void createTest() {
        webTestClient.get()
                .uri("/user")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message")
                .isEqualTo("User saved");
    }
}
