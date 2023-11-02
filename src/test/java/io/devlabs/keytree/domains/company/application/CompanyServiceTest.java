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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("기업 리스트 조회")
    void getCompanies() {
        // given
        Company firstCompany = createCompanyEntity(1L, createCompanyRequest());
        Company secondCompany = createCompanyEntity(2L, createCompanyRequest());
        List<Company> company = List.of(firstCompany, secondCompany);

        when(companyRepository.findAll()).thenReturn(company);

        // when
        List<CreateCompanyResponse> foundCompany = companyService.getCompanies();
        List<Long> companyIds = foundCompany.stream().map(CreateCompanyResponse::getId).toList();

        // then
        assertThat(foundCompany.size()).isEqualTo(company.size());
        assertThat(companyIds.contains(firstCompany.getId())).isTrue();
        assertThat(companyIds.contains(secondCompany.getId())).isTrue();
    }

    @Test
    @DisplayName("기업 아이디로 특정 기업 조회")
    void getCompanyById() {
        // given
        Company company = createCompanyEntity(1L, createCompanyRequest());
        when(companyRepository.findById(any(Long.class))).thenReturn(Optional.of(company));

        // when
        CreateCompanyResponse response = companyService.getCompanyById(company.getId());

        // then
        assertThat(response.getId()).isEqualTo(company.getId());
        assertThat(response.getName()).isEqualTo(company.getName());
        assertThat(response.getAddress()).isEqualTo(company.getAddress());
        assertThat(response.getPhone()).isEqualTo(company.getPhone());
    }

    @Test
    @DisplayName("기업 아이디로 기업 삭제시 deleteById()가 한 번 수행됨")
    void removeCompanyByIdCallDeleteByIdOnce() {
        // when
        companyService.removeCompanyById(1L);

        // when, then
        verify(companyRepository, times(1)).deleteById(any(Long.class));
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
        request.setAddress("서울특별시 강남구");
        request.setPhone("010-1111-1111");

        return request;
    }
}
