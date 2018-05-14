package com.noser.demo.repo.jpa;


import com.noser.demo.model.jpa.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //TODO GET Customers with Birthdate before XY, order by name

    //TODO GET all Customers order by name, firstname


}

