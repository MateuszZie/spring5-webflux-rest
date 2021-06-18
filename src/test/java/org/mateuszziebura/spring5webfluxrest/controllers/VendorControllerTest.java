package org.mateuszziebura.spring5webfluxrest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mateuszziebura.spring5webfluxrest.Repositories.VendorRepository;
import org.mateuszziebura.spring5webfluxrest.domain.Vendor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorController vendorController;
    VendorRepository vendorRepository;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }


    @Test
    void getVendors() {
        BDDMockito.given(vendorRepository.findAll()).willReturn(Flux.just(new Vendor(), new Vendor()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendor() {
        BDDMockito.given(vendorRepository.findById("yo")).willReturn(Mono.just(new Vendor()));

        webTestClient.get()
                .uri("/api/v1/vendors/um")
                .exchange()
                .expectBody(Vendor.class);
    }
}