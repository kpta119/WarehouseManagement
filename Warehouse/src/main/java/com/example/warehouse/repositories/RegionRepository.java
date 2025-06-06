package com.example.warehouse.repositories;

import com.example.warehouse.domain.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<Region, Integer> {
}
