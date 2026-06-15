package com.minicommerce.account_service.repository;

import org.springframework.data.repository.CrudRepository;

import com.minicommerce.account_service.entity.OtpRegistration;

public interface OtpRegistrationRepository extends CrudRepository<OtpRegistration, String>{
    
}
