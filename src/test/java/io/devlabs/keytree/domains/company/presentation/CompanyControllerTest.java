package io.devlabs.keytree.domains.company.presentation;

import io.devlabs.keytree.domains.company.application.CompanyService;
import io.devlabs.keytree.domains.company.application.dto.CreateCompanyRequest;
import io.devlabs.keytree.domains.company.application.dto.CreateCompanyResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

public class CompanyControllerTest {


  @Autowired
  CompanyService companyService;

  @LocalServerPort
  private int port;

  @DisplayName("기업 등록 API")
  @Test
  void createCompany() {
    // given
    CreateCompanyRequest requestBody = createCompanyRequest();

    // when
    ExtractableResponse<Response> response =
        RestAssured.given()
            .log()
            .all()
            .body(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(
                document(
                    "index", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .when()
            .post("/companies")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }

  @DisplayName("기업 리스트 조회 API")
  @Test
  void getSchedules() {
    // given
    companyService.createCompany(createCompanyRequest());
    companyService.createCompany(createCompanyRequest());

    // when
    ExtractableResponse<Response> response =
        RestAssured.given()
            .log()
            .all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/companies")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.body().jsonPath().getList("$").size()).isEqualTo(2);
  }

  @DisplayName("기업 인덱스로 상세 조회 API")
  @Test
  void getCompanyById() {
    // given
    CreateCompanyResponse company = companyService.createCompany(createCompanyRequest());

    // when
    ExtractableResponse<Response> response =
        RestAssured.given()
            .pathParam("companyId", company.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .log()
            .all()
            .when()
            .get("/companies/{companyId}")
            .then()
            .log()
            .all()
            .extract();

    // then
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.body().jsonPath().getLong("id")).isEqualTo(company.getId());
    assertThat(response.body().jsonPath().getString("name")).isEqualTo(company.getName());
    assertThat(response.body().jsonPath().getString("phone")).isEqualTo(company.getPhone());
    assertThat(response.body().jsonPath().getString("address")).isEqualTo(company.getAddress());
  }

  private CreateCompanyRequest createCompanyRequest() {
    CreateCompanyRequest request = new CreateCompanyRequest();
    request.setName("A기업");
    request.setPhone("01011112222");
    request.setAddress("서울특별시 강남구");

    return request;
  }
}
