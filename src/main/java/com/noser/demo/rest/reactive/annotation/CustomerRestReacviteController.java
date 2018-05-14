package com.noser.demo.rest.reactive.annotation;


import com.noser.demo.model.mongo.Customer;
import com.noser.demo.repo.mongo.CustomerReactiveRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;


@Log4j2
@RestController
@RequestMapping("/api/reactive/annotation")
public class CustomerRestReacviteController {

    //TODO inject CustomerReactiveRepository
    private final CustomerReactiveRepository customerReactiveRepository;

    @Autowired
    public CustomerRestReacviteController(CustomerReactiveRepository customerReactiveRepository) {
        this.customerReactiveRepository = customerReactiveRepository;
    }


    //TODO GET mapping for "/customers/all"
    @GetMapping("/customers/all")
    public Flux<Customer> getAllCustomers() {
        return customerReactiveRepository.findAll();
    }


    //TODO GET mapping for "/customers/{id}"
    @GetMapping("/customers/{id}")
    public Mono<Customer> getCustomerById(String id) {
        return customerReactiveRepository.findById(id);
    }

    //TODO POST mapping for "/customers" with customer request body
    @PostMapping("/customers")
    public ResponseEntity<Mono<Customer>> createCustomer(@RequestBody Customer customer) {
        Mono<Customer> createdCustomer = customerReactiveRepository.save(customer);
        if (createdCustomer.blockOptional(Duration.ofSeconds(2)).isPresent()) {
            return ResponseEntity.created(URI.create("customers/" + createdCustomer.block().getId())).body(createdCustomer);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}