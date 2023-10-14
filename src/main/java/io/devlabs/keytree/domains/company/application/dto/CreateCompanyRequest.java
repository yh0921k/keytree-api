package io.devlabs.keytree.domains.company.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyRequest {
    private String name;
    private String phone;
    private String address;
}
