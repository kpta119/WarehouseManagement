package com.example.warehouse.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private AddressDto address;
}
