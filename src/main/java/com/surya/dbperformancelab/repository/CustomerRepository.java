package com.surya.dbperformancelab.repository;

import com.surya.dbperformancelab.schema.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}