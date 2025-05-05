package com.antonio.repo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DbInit implements CommandLineRunner {

    @Value("${app.database.path}")
    private String dbPath;

    @Override
    public void run(String... args) throws Exception {
        Path path = Paths.get(dbPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}