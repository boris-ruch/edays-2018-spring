package com.noser.demo.rest.servlet;

import com.noser.demo.model.jpa.Customer;
import com.noser.demo.repo.jpa.CustomerRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static java.time.LocalDate.now;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository repository;


    @Test
    @WithMockUser
    public void getCustomer_AuthenticatedUser_successfully() throws Exception {
        this.mockMvc.perform(get("/api/servlet/customers/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Joe")))
                .andExpect(jsonPath("$.lastname", is("Doe")));
    }


    @Test
    @Disabled
    public void getCustomer_NoAuthenticatedUser_notAuthenticated() throws Exception {
        this.mockMvc.perform(get("/api/servlet/customers/1")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void createCustomer_getCustomer_successfully() throws Exception {
        // arrange
        Customer customer = repository.save(Customer.builder().name("Testuser Name").birthday(now()).lastname("Testuser Lastname").build());
        // act & assert
        this.mockMvc.perform(get("/api/servlet/customers/" + customer.getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(customer.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Testuser Name")))
                .andExpect(jsonPath("$.lastname", is("Testuser Lastname")));
    }


}