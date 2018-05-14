package com.noser.demo.rest.reactive.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutingConfiguration {


    @Bean
    RouterFunction<ServerResponse> routes(CustomerRestHandler handler) {
        return route(GET("/api/reactive/functional/customers/all").and(accept(APPLICATION_JSON)), handler::getCustomers)
                .andRoute(GET("/api/reactive/functional/customers/{id}").and(accept(APPLICATION_JSON)), handler::getCustomer)
                .andRoute(GET("/api/reactive/functional/customers").and(accept(APPLICATION_JSON)), handler::getCustomersByLastNameOrderByName)
                .andRoute(POST("/api/reactive/functional/customers").and(accept(APPLICATION_JSON)), handler::addCustomer);
    }
}
