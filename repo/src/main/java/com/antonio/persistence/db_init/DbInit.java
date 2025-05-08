package com.antonio.persistence.db_init;

import com.antonio.persistence.entity.ObjectToGenerator;
import com.antonio.persistence.repository.ObjectToGeneratorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbInit implements CommandLineRunner {

    private final ObjectToGeneratorRepository objectRepository;

    public DbInit(ObjectToGeneratorRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<ObjectToGenerator> objects = ObjectDataLoader.loadObjects();
        objectRepository.saveAll(objects);

        objectRepository.findAll().forEach(o ->
                System.out.println(o.getKeyName() + " | " + o.getRusName() + " | " + o.getEngName())
        );
    }
}
