package com.example.warehouse.repositories;

import com.example.warehouse.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer>, JpaRepository<Employee, Integer> {
}
