package com.surya.dbperformancelab.controller;

import com.surya.dbperformancelab.dto.responseDto.CustomerResponse;
import com.surya.dbperformancelab.service.interfaces.CustomerServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceInterface customerServiceInterface;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerServiceInterface.getCustomerById(id));
    }
}
