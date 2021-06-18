package org.mateuszziebura.spring5webfluxrest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mateuszziebura.spring5webfluxrest.Repositories.CategoryRepository;
import org.mateuszziebura.spring5webfluxrest.domain.Category;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryController categoryController;
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void categoryList() {
        BDDMockito.given(categoryRepository.findAll()).willReturn(Flux.just(Category.builder().description("yoo").build(),
                Category.builder().description("hi").build()));

        webTestClient
                .get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void category() {
        BDDMockito.given(categoryRepository.findById("lol")).willReturn(Mono.just(Category.builder().build()));

        webTestClient.get()
                .uri("/api/v1/categories/lol")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    void createCategory() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(Category.builder().description("lol").build()));

        Mono<Category> category = Mono.just(Category.builder().description("some").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(category, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();

    }

    @Test
    void updateCategory() {
        BDDMockito.given(categoryRepository.save(any(Category.class))).willReturn(Mono.just(Category.builder().description("some").build()));
        Mono<Category> category = Mono.just(Category.builder().description("some").build());

        webTestClient.put()
                .uri("/api/v1/categories/ss")
                .body(category, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

    }
}