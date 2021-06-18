package org.mateuszziebura.spring5webfluxrest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mateuszziebura.spring5webfluxrest.Repositories.VendorRepository;
import org.mateuszziebura.spring5webfluxrest.domain.Vendor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
        given(vendorRepository.findAll()).willReturn(Flux.just(new Vendor(), new Vendor()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendor() {
        given(vendorRepository.findById("yo")).willReturn(Mono.just(new Vendor()));

        webTestClient.get()
                .uri("/api/v1/vendors/um")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void createVendor() {
        given(vendorRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(new Vendor()));

        Mono<Vendor> vendorMono = Mono.just(new Vendor());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void updateVendor() {
        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(new Vendor()));

        Mono<Vendor> vendorMono = Mono.just(new Vendor());

        webTestClient.put()
                .uri("/api/v1/vendors/um")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }


    @Test
    void pathVendor() {
        given(vendorRepository.findById(anyString())).willReturn(Mono.just(new Vendor()));
        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(new Vendor()));

        Vendor vendor =new Vendor();
        vendor.setFirstName("Mateusz");
        vendor.setLastName("Ziebura");
        Mono<Vendor> vendorMono = Mono.just(vendor);

        webTestClient.patch()
                .uri("/api/v1/vendors/um")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository).save(any());
    }
    @Test
    void pathVendorFail() {
        given(vendorRepository.findById(anyString())).willReturn(Mono.just(new Vendor()));
        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(new Vendor()));

        Vendor vendor =new Vendor();
        Mono<Vendor> vendorMono = Mono.just(vendor);

        webTestClient.patch()
                .uri("/api/v1/vendors/um")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository, never()).save(any());
    }
}