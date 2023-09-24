package io.devlabs.keytree.domains.company.application;

import io.devlabs.keytree.domains.company.application.dto.CreateCompanyRequest;
import io.devlabs.keytree.domains.company.application.dto.CreateCompanyResponse;
import io.devlabs.keytree.domains.company.domain.Company;
import io.devlabs.keytree.domains.company.domain.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @Test
    @DisplayName("기업 생성")
    void createCompany() {
        // given
        CreateCompanyRequest request = createCompanyRequest();
        Company company = createCompanyEntity(1L, createCompanyRequest());

        when(companyRepository.save(any(Company.class))).thenReturn(company);

        // when
        CreateCompanyResponse response = companyService.createCompany(request);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo(company.getName());
        assertThat(response.getAddress()).isEqualTo(company.getAddress());
        assertThat(response.getPhone()).isEqualTo(company.getPhone());
    }

    private Company createCompanyEntity(Long companyId, CreateCompanyRequest request) {
        return Company.builder()
                .id(companyId)
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .build();
    }

    private CreateCompanyRequest createCompanyRequest() {
        CreateCompanyRequest request = new CreateCompanyRequest();
        request.setName("A기업");
        request.setAddress("서울특별시");
        request.setPhone("010-1111-1111");

        return request;
    }
}
