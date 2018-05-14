package com.noser.demo.rest.reactive.functional;


import com.noser.demo.model.mongo.Customer;
import com.noser.demo.repo.mongo.CustomerReactiveRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;


@Component
public class CustomerRestHandler {
    private final CustomerReactiveRepository repository;

    CustomerRestHandler(CustomerReactiveRepository repository) {
        this.repository = repository;
    }


    public Mono<ServerResponse> getCustomers(ServerRequest request) {
        Flux<Customer> customers = repository.findAll();
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(customers, Customer.class);
    }


    public Mono<ServerResponse> addCustomer(ServerRequest request) {
        Mono<Customer> customer = request.bodyToMono(Customer.class);
        return customer.flatMap(c ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(repository.save(c), Customer.class));
    }


    public Mono<ServerResponse> getCustomer(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Customer> customer = repository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return customer
                .flatMap(c -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(fromObject(c)))
                .switchIfEmpty(notFound);
    }


    public Mono<ServerResponse> getCustomersByLastNameOrderByName(ServerRequest request) {
        String lastname = request.pathVariable("lastname");
        Flux<Customer> customers = repository.findAllByLastnameStartingWithOrderByName(lastname);
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(customers, Customer.class);
    }


}