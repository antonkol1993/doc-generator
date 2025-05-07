package com.antonio.persistence.config;

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
        List<ObjectToGenerator> objects = List.of(
                create("Шуруп универсальный", "Universal Screw", "ШурупУниверсальный", "/images/screw1.jpg"),
                create("Саморез по металлу", "Metal Self-tapping Screw", "СаморезМеталл", "/images/screw2.jpg"),
                create("Гайка М8", "Nut M8", "ГайкаМ8", "/images/nut1.jpg"),
                create("Шайба гроверная", "Spring Washer", "ШайбаГровер", "/images/washer1.jpg"),
                create("Шуруп для дерева", "Wood Screw", "ШурупДерево", "/images/screw3.jpg"),
                create("Болт с шестигранной головкой", "Hex Head Bolt", "БолтШестигр", "/images/bolt1.jpg")
        );

        objectRepository.saveAll(objects);

        objectRepository.findAll().forEach(o ->
                System.out.println(o.getOriginalName() + " | " + o.getRusName())
        );
    }

    private ObjectToGenerator create(String rusName, String originalName, String to1C8, String imagePath) {
        ObjectToGenerator obj = new ObjectToGenerator();
        obj.setOriginalName(originalName);
        obj.setRusName(rusName);
        obj.setTo1C8name(to1C8);
        obj.setImagePath(imagePath);
        return obj;
    }
}
