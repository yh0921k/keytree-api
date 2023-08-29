package io.devlabs.keytree.domains.user.presentation;

import io.devlabs.keytree.domains.user.application.UserService;
import io.devlabs.keytree.domains.user.application.dto.CreateUserRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
  @Autowired UserService userService;

  @LocalServerPort private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  @DisplayName("사용자 등록 API")
  public void createUser() {
    // given
    CreateUserRequest request = createUserRequest();

    // when
    ExtractableResponse<Response> response =
        RestAssured.given()
            .log()
            .all()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/users")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }

  private CreateUserRequest createUserRequest() {
    LocalDateTime startedAt =
        LocalDateTime.parse(
            "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    CreateUserRequest request = new CreateUserRequest();
    request.setStartedAt(startedAt);
    request.setName("KeyTree");
    request.setPhone("01011112222");
    request.setEmail("KeyTree");

    return request;
  }
}
