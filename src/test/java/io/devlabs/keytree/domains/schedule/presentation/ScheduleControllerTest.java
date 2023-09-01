package io.devlabs.keytree.domains.schedule.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import io.devlabs.keytree.domains.schedule.application.ScheduleService;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleRequest;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
class ScheduleControllerTest {

  @Autowired ScheduleService scheduleService;

  @LocalServerPort private int port;

  private RequestSpecification spec;

  @BeforeEach
  void setUp(RestDocumentationContextProvider restDocumentation) {
    RestAssured.port = port;
    this.spec =
        new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation)).build();
  }

  @DisplayName("일정 등록 API")
  @Test
  void createSchedule() {
    // given
    CreateScheduleRequest requestBody = createScheduleRequest();

    // when
    ExtractableResponse<Response> response =
        RestAssured.given(this.spec)
            .log()
            .all()
            .body(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(
                document(
                    "index", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .when()
            .post("/schedules")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }

  @DisplayName("일정 리스트 조회 API")
  @Test
  void getSchedules() {
    // given
    scheduleService.createSchedule(createScheduleRequest());
    scheduleService.createSchedule(createScheduleRequest());

    // when
    ExtractableResponse<Response> response =
        RestAssured.given()
            .log()
            .all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/schedules")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.body().jsonPath().getList("$").size()).isEqualTo(2);
  }

  @DisplayName("일정 아이디로 일정 상세 조회 API")
  @Test
  void getScheduleById() {
    // given
    CreateScheduleResponse schedule = scheduleService.createSchedule(createScheduleRequest());

    // when
    ExtractableResponse<Response> response =
        RestAssured.given()
            .pathParam("scheduleId", schedule.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .log()
            .all()
            .when()
            .get("/schedules/{scheduleId}")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.body().jsonPath().getLong("id")).isEqualTo(schedule.getId());
    assertThat(response.body().jsonPath().getString("title")).isEqualTo(schedule.getTitle());
    assertThat(response.body().jsonPath().getString("contents")).isEqualTo(schedule.getContents());
  }

  @DisplayName("일정 아이디로 일정 삭제 API")
  @Test
  void removeScheduleById() {
    // given
    CreateScheduleResponse schedule = scheduleService.createSchedule(createScheduleRequest());

    // when
    ExtractableResponse<Response> removeScheduleResponse =
        RestAssured.given()
            .pathParam("scheduleId", schedule.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .log()
            .all()
            .when()
            .delete("/schedules/{scheduleId}")
            .then()
            .log()
            .all()
            .extract();

    ExtractableResponse<Response> getScheduleResponse =
        RestAssured.given()
            .pathParam("scheduleId", schedule.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .log()
            .all()
            .when()
            .get("/schedules/{scheduleId}")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(removeScheduleResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

    // TODO: 현재 전역 예외 처리기가 구현되어 있지 않아, 500 상태를 그대로 검증하지만 추후 개선 필요
    assertThat(getScheduleResponse.statusCode())
        .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  private CreateScheduleRequest createScheduleRequest() {
    LocalDateTime startedAt =
        LocalDateTime.parse(
            "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTime finishedAt =
        LocalDateTime.parse(
            "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    CreateScheduleRequest request = new CreateScheduleRequest();
    request.setStartedAt(startedAt);
    request.setFinishedAt(finishedAt);
    request.setTitle("Title");
    request.setContents("Contents");

    return request;
  }
}
