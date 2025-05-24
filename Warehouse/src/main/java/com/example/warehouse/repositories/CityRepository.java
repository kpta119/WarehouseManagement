package com.example.warehouse.repositories;

import com.example.warehouse.domain.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {
    Optional<City> findByPostalCodeAndNameAndCountry_Id(String postalCode, String city, Integer countryId);
}
