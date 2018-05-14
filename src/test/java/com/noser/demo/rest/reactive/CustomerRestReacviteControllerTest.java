package com.noser.demo.rest.reactive;

import com.noser.demo.model.mongo.Customer;
import com.noser.demo.repo.jpa.CustomerRepository;
import com.noser.demo.repo.mongo.CustomerReactiveRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebFluxTest
public class CustomerRestReacviteControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CustomerReactiveRepository repository;

    @MockBean
    private CustomerRepository customerRepo;

    @MockBean
    private ApplicationRunner applicationRunner;

    @Test
    public void test_annotationBased_getCustomerJoe_successfully() {

        //arrange
        Customer joe = Customer.builder()
                .id(UUID.randomUUID().toString())
                .name("Joe")
                .lastname("Doe")
                .birthday(LocalDate.now())
                .build();
        when(repository.findAll()).thenReturn(Flux.just(joe));

        //act & assert
        webClient.get().uri("/api/reactive/annotation/customers/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Customer.class).hasSize(1).contains(joe);

    }

    @Test
    public void createCustomer_successfully() {

        //arrange
        Mono<Customer> testCustomer = Mono.just(Customer.builder()
                .name("test-name")
                .lastname("test-lastname")
                .birthday(LocalDate.of(2000, Month.JANUARY, 1))
                .build());

        when(repository.save(testCustomer.block())).thenReturn(testCustomer);

        //act & assert
        webClient.post().uri("/api/reactive/annotation/customers")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(testCustomer, Customer.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(Customer.class).hasSize(1).contains(testCustomer.block());

    }

    @Test
    public void createCustomerFailed_afterTimeout(){

        //arrange
        Mono<Customer> testCustomer = Mono.just(Customer.builder()
                .name("test-name")
                .lastname("test-lastname")
                .birthday(LocalDate.of(2000, Month.JANUARY, 1))
                .build());
        when(repository.save(testCustomer.block())).thenReturn(testCustomer.delayElement(Duration.ofSeconds(3)));

        //act & assert
        webClient.post().uri("/api/reactive/annotation/customers")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(testCustomer, Customer.class)
                .exchange()
                .expectStatus().is5xxServerError();

    }


}