package org.mateuszziebura.spring5webfluxrest.controllers;

import org.mateuszziebura.spring5webfluxrest.Repositories.VendorRepository;
import org.mateuszziebura.spring5webfluxrest.domain.Vendor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    public Flux<Vendor> getVendors(){
        return vendorRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<Vendor> getVendor(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorPublisher){
        return vendorRepository.saveAll(vendorPublisher).then();
    }

    @PutMapping("/{id}")
    public Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/{id}")
    public Mono<Vendor> pathVendor(@PathVariable String id, @RequestBody Vendor vendor){

        Vendor vendorFromRepository = vendorRepository.findById(id).block();

        return vendorRepository.save(vendor);
    }
}
