package org.mateuszziebura.spring5webfluxrest.controllers;

import org.mateuszziebura.spring5webfluxrest.Repositories.CategoryRepository;
import org.mateuszziebura.spring5webfluxrest.domain.Category;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public Flux<Category> categoryList(){
        return categoryRepository.findAll();
    }
    @GetMapping("/{id}")
    public Mono<Category> category(@PathVariable String id){
        return categoryRepository.findById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createCategory(@RequestBody Publisher<Category> publisher){
        return categoryRepository.saveAll(publisher).then();
    }

    @PutMapping("/{id}")
    public Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category){
        category.setId(id);
        return categoryRepository.save(category);
    }
    @PatchMapping("/{id}")
    public Mono<Category> pathCategory(@PathVariable String id, @RequestBody Category category){

        Category category1 = categoryRepository.findById(id).block();

        if (category1.getDescription() != category.getDescription()){
            category1.setDescription(category.getDescription());
            return categoryRepository.save(category1);
        }
        return Mono.just(category1);
    }
}
