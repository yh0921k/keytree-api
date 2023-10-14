package io.devlabs.keytree.domains.company.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class CompanyRepositoryTest {

  @Autowired
  CompanyRepository companyRepository;

  @Test
  @DisplayName("기업 엔티티 저장")
  void save() {
    // given
    Company company =
        Company.builder()
            .name("A회사")
            .phone("01011112222")
            .address("서울특별시 강남구")
            .build();

    // when
    Company savedCompany = companyRepository.save(company);

    // then
    assertThat(savedCompany.getId()).isGreaterThan(0L);
    assertThat(savedCompany.getName()).isEqualTo(company.getName());
    assertThat(savedCompany.getPhone()).isEqualTo(company.getPhone());
    assertThat(savedCompany.getAddress()).isEqualTo(company.getAddress());
  }

  @Test
  @DisplayName("기업 엔티티 리스트 조회")
  void findAll() {
    // given
    Company firstCompany = createCompany();
    Company secondCompany = createCompany();
    companyRepository.saveAll(List.of(firstCompany, secondCompany));

    // when
    List<Company> companies = companyRepository.findAll();
    List<Long> companyIds = companies.stream().map(Company::getId).toList();

    // then
    assertThat(companies.size()).isEqualTo(2);
    assertThat(companyIds.contains(1L)).isTrue();
    assertThat(companyIds.contains(2L)).isTrue();
  }

  private Company createCompany() {
    return Company.builder()
        .name("A기업")
        .phone("01011112222")
        .address("서울특별시 강남구")
        .build();
  }
}
