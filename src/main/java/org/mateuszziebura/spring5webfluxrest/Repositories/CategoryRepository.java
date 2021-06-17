package org.mateuszziebura.spring5webfluxrest.Repositories;

import org.mateuszziebura.spring5webfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category,String> {
}
