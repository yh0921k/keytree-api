package io.devlabs.keytree.domains.company.application.dto;

import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateCompanyResponse {
    private Long id;
    private String name;
    private String phone;
    private String address;
}
