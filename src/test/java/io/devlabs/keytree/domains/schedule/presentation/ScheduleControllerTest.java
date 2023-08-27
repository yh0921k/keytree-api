package io.devlabs.keytree.domains.schedule.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import io.devlabs.keytree.domains.schedule.application.ScheduleService;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleRequest;
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

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ScheduleControllerTest {

  @Autowired
  ScheduleService scheduleService;

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @DisplayName("일정 등록 API")
  @Test
  void createSchedule() {
    // given
    CreateScheduleRequest requestBody = createScheduleRequest();

    // when
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .body(requestBody)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post("/schedules")
        .then().log().all()
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
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get("/schedules")
        .then().log().all()
        .extract();

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.body().jsonPath().getList("$").size()).isEqualTo(2);
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
