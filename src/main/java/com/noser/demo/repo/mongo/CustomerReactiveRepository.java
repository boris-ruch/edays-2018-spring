package com.noser.demo.repo.mongo;


import com.noser.demo.model.mongo.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CustomerReactiveRepository extends ReactiveMongoRepository<Customer, String> {


    Flux<Customer> findAllByLastnameStartingWithOrderByName(String lastname);


}

