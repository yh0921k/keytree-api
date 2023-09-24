package io.devlabs.keytree.domains.company.presentation;

import io.devlabs.keytree.domains.company.application.CompanyService;
import io.devlabs.keytree.domains.company.application.dto.CreateCompanyRequest;
import io.devlabs.keytree.domains.company.application.dto.CreateCompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/companies")
    public CreateCompanyResponse createCompany(@RequestBody CreateCompanyRequest request) {
        return companyService.createCompany(request);
    }
}
