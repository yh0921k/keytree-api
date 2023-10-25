package io.devlabs.keytree.domains.company.application;

import io.devlabs.keytree.domains.company.application.dto.CreateCompanyRequest;
import io.devlabs.keytree.domains.company.application.dto.CreateCompanyResponse;
import io.devlabs.keytree.domains.company.domain.Company;
import io.devlabs.keytree.domains.company.domain.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public CreateCompanyResponse createCompany(CreateCompanyRequest request) {
        Company company =
                Company.builder()
                        .name(request.getName())
                        .address(request.getAddress())
                        .phone(request.getPhone())
                        .build();

        Company savedCompany = companyRepository.save(company);

        return CreateCompanyResponse.builder()
                .id(savedCompany.getId())
                .name(savedCompany.getName())
                .address(savedCompany.getAddress())
                .phone(savedCompany.getPhone())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CreateCompanyResponse> getCompanies() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream()
            .map(
                company ->
                    CreateCompanyResponse.builder()
                        .id(company.getId())
                        .name(company.getName())
                        .address(company.getAddress())
                        .phone(company.getPhone())
                        .build())
            .toList();
    }

    @Transactional(readOnly = true)
    public CreateCompanyResponse getCompanyById(Long id) {
        Company company = companyRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 기업입니다."));

        return CreateCompanyResponse.builder()
            .id(company.getId())
            .name(company.getName())
            .address(company.getAddress())
            .phone(company.getPhone())
            .build();
    }

}
