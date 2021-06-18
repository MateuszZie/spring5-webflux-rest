package org.mateuszziebura.spring5webfluxrest.Repositories;

import org.mateuszziebura.spring5webfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor,String> {
}
