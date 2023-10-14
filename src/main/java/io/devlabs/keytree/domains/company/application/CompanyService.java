package io.devlabs.keytree.domains.company.application;

import io.devlabs.keytree.domains.company.application.dto.CreateCompanyRequest;
import io.devlabs.keytree.domains.company.application.dto.CreateCompanyResponse;
import io.devlabs.keytree.domains.company.domain.Company;
import io.devlabs.keytree.domains.company.domain.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
