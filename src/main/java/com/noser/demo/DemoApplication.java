package com.noser.demo;

import com.noser.demo.repo.jpa.CustomerRepository;
import com.noser.demo.repo.mongo.CustomerReactiveRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@Log4j2
public class DemoApplication {


    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerReactiveRepository customerReactiveRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {

        // TODO: Relax Binding
        List<com.noser.demo.model.jpa.Customer> jpaCustomers = Binder.get(environment)
                .bind("customer", Bindable.listOf(com.noser.demo.model.jpa.Customer.class))
                .orElseThrow(IllegalStateException::new);

        List<com.noser.demo.model.mongo.Customer> mongoCustomers = Binder.get(environment)
                .bind("customer", Bindable.listOf(com.noser.demo.model.mongo.Customer.class))
                .orElseThrow(IllegalStateException::new);


        return args -> {
            customerRepository.saveAll(jpaCustomers);
            customerReactiveRepository.saveAll(mongoCustomers).subscribe();
        };
    }


}








