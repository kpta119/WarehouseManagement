package com.example.warehouse.repositories;

import com.example.warehouse.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    List<Country> findByRegionId(Integer regionId);

    @Query("SELECT c FROM Country c WHERE (:regionId IS NULL OR c.region.id = :regionId)")
    List<Country> findAllByRegionId(Integer regionId);
}
