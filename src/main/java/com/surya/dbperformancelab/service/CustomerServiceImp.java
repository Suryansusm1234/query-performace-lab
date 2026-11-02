package com.surya.dbperformancelab.service;

import com.surya.dbperformancelab.dto.responseDto.CustomerResponse;
import com.surya.dbperformancelab.exception.ResourceNotFoundException;
import com.surya.dbperformancelab.repository.CustomerRepository;
import com.surya.dbperformancelab.schema.Customer;
import com.surya.dbperformancelab.service.interfaces.CustomerServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerServiceInterface {
    private final CustomerRepository customerRepository;
    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer resultCustomer =  customerRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Customer not found with the id:"+id));
        return CustomerResponse.builder()
                .id(resultCustomer.getId())
                .fullName(resultCustomer.getName())
                .email(resultCustomer.getEmail())
                .createdAt(resultCustomer.getRegisteredAt())
                .build();
    }
}
