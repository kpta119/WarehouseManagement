package com.example.warehouse.repositories;

import com.example.warehouse.domain.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<Report, Integer> {
}
