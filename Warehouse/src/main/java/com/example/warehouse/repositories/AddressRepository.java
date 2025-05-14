package com.example.warehouse.repositories;

import com.example.warehouse.domain.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer> {
}
