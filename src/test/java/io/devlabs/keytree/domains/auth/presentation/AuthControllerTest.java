package io.devlabs.keytree.domains.auth.presentation;

<<<<<<< HEAD
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import io.devlabs.keytree.domains.auth.application.AuthService;
import io.devlabs.keytree.domains.auth.application.dto.SignInUserRequest;
import io.devlabs.keytree.domains.auth.infrastructure.PasswordUtils;
import io.devlabs.keytree.domains.user.domain.User;
import io.devlabs.keytree.domains.user.domain.UserRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
=======
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
>>>>>>> 99b7042 (feat: 로그아웃 API 핸들러 구현)
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
<<<<<<< HEAD
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
class AuthControllerTest {
  @Autowired UserRepository userRepository;

  @Autowired PasswordUtils passwordUtils;

  @Autowired AuthService authService;

  @LocalServerPort private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @DisplayName("로그인 API")
  @Test
  void signIn() {
    // given
    SignInUserRequest requestBody =
        SignInUserRequest.builder()
            .email("keytree@devlabs.io")
            .password("TestPassword1234!")
            .build();

    userRepository.save(
        User.builder()
            .email(requestBody.getEmail())
            .password(passwordUtils.encrypt(requestBody.getPassword()))
            .phone("010-1111-2222")
            .name("keytree")
            .startedAt(LocalDateTime.now())
            .build());

    // when
    ExtractableResponse<Response> response =
        RestAssured.given()
            .log()
            .all()
            .body(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/signIn")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.cookie("JSESSIONID")).isNotNull();
  }
=======
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("로그아웃 API")
    @Test
    void logout() {
        // given

        // when
        ExtractableResponse<Response> response =
                RestAssured.given()
                        .log()
                        .all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .when()
                        .post("/logout")
                        .then()
                        .log()
                        .all()
                        .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
>>>>>>> 99b7042 (feat: 로그아웃 API 핸들러 구현)
}
