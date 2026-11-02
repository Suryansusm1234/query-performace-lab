package com.surya.dbperformancelab.service.interfaces;

import com.surya.dbperformancelab.dto.responseDto.CustomerResponse;

public interface CustomerServiceInterface {
    CustomerResponse getCustomerById(Long id);
}
