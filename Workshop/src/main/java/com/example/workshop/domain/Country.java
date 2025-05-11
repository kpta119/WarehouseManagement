package com.example.workshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private Long id;
    private String name;
    private String countryCode;
    private Long regionId;
}
