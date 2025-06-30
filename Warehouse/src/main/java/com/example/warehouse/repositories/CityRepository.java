package com.example.warehouse.repositories;

import com.example.warehouse.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByPostalCodeAndNameAndCountry_Id(String postalCode, String city, Integer countryId);
}
