package com.noser.demo.rest.servlet;


import com.noser.demo.model.jpa.Customer;
import com.noser.demo.repo.jpa.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/servlet")
public class CustomerRestController {
    private final CustomerRepository repository;

    @Autowired
    CustomerRestController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers/all")
    public Collection<Customer> getCustomers() {
        return this.repository.findAll();
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable long id) {
        return repository.findById(id).map(c -> ResponseEntity.ok(c)).orElse(ResponseEntity.notFound().build());
    }


}