package com.panacare.panabeans.data.repository;

import com.panacare.panabeans.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
