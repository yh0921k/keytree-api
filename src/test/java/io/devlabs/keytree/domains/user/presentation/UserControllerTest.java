package io.devlabs.keytree.domains.user.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import io.devlabs.keytree.domains.user.application.UserService;
import io.devlabs.keytree.domains.user.application.dto.CreateUserRequest;
import io.devlabs.keytree.domains.user.application.dto.CreateUserResponse;
import io.devlabs.keytree.domains.user.application.dto.GenerateCreateUserRequest;
import io.devlabs.keytree.domains.user.application.dto.GenerateModifyUserRequest;
import io.devlabs.keytree.domains.user.application.dto.ModifyUserRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
    CreateUserRequest request = GenerateCreateUserRequest.generate();

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

  @Test
  @DisplayName("사용자 수정 API")
  public void modifyUser() {
    // given
    CreateUserRequest createUserRequest = GenerateCreateUserRequest.generate();

    ExtractableResponse<Response> createUserResponse =
        RestAssured.given()
            .body(createUserRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/users")
            .then()
            .extract();

    // when
    ModifyUserRequest modifyUserRequest = GenerateModifyUserRequest.generate();
    Long userId = createUserResponse.jsonPath().getLong("id");

    ExtractableResponse<Response> modifyUserResponse =
        RestAssured.given()
            .log()
            .all()
            .pathParam("userId", userId)
            .body(modifyUserRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .patch("/users/{userId}")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(modifyUserResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("사용자 아이디로 사용자 상세 조회 API")
  public void getUserById() {
    // given
    CreateUserResponse foundUser = userService.createUser(GenerateCreateUserRequest.generate());

    // when
    ExtractableResponse<Response> response =
        RestAssured.given()
            .pathParam("userId", foundUser.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .log()
            .all()
            .when()
            .get("/users/{userId}")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.jsonPath().getLong("id")).isEqualTo(foundUser.getId());
    assertThat(response.jsonPath().getString("startedAt"))
        .isEqualTo(
            foundUser.getStartedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    assertThat(response.jsonPath().getString("email")).isEqualTo(foundUser.getEmail());
    assertThat(response.jsonPath().getString("name")).isEqualTo(foundUser.getName());
    assertThat(response.jsonPath().getString("phone")).isEqualTo(foundUser.getPhone());
    assertThat(response.jsonPath().getString("address")).isEqualTo(foundUser.getAddress());
  }

  @DisplayName("사용자 리스트 조회 API")
  @Test
  void getUsers() {
    // given
    userService.createUser(GenerateCreateUserRequest.generate());
    userService.createUser(GenerateCreateUserRequest.generate());

    // when
    ExtractableResponse<Response> response =
        RestAssured.given()
            .log()
            .all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/users")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.body().jsonPath().getList("$").size()).isEqualTo(2);
  }
}
