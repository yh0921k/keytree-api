package io.devlabs.keytree.domains.attendance.presentation;

import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AttendanceControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("출근 등록 API")
    @Test
    void createStartAttendance() {
        // given
        CreateStartAttendanceRequest requestBody = createStartAttendanceRequest();

        // when
        ExtractableResponse<Response> response =
                RestAssured.given()
                        .log()
                        .all()
                        .body(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .when()
                        .post("/start/attendances")
                        .then()
                        .log()
                        .all()
                        .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private CreateStartAttendanceRequest createStartAttendanceRequest() {
        LocalDateTime startedAt =
                LocalDateTime.parse(
                        "2023-08-29 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Long userId = 1L;

        CreateStartAttendanceRequest request = new CreateStartAttendanceRequest();
        request.setUserId(userId);
        request.setStartedAt(startedAt);

        return request;
    }
}
