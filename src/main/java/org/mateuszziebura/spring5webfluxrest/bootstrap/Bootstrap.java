package org.mateuszziebura.spring5webfluxrest.bootstrap;

import org.mateuszziebura.spring5webfluxrest.Repositories.VendorRepository;
import org.mateuszziebura.spring5webfluxrest.domain.Vendor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Bootstrap implements CommandLineRunner{

    private final VendorRepository vendorRepository;

    public Bootstrap(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(vendorRepository.count().block()==0){
            load();
            System.out.println("loading");
        }
        System.out.println("Data loaded " + vendorRepository.count().block());
    }

    private void load() {
        Vendor adam = new Vendor();
        adam.setFirstName("Adam");
        adam.setLastName("Ryt");

        Vendor mateusz = new Vendor();
        mateusz.setFirstName("Mateusz");
        mateusz.setLastName("Ziebura");

        Vendor krystian = new Vendor();
        krystian.setFirstName("Krystian");
        krystian.setLastName("Lis");

        vendorRepository.save(adam).block();
        vendorRepository.save(mateusz).block();
        vendorRepository.save(krystian).block();
    }
}
