package com.antonio.docgenerator;

import com.antonio.docgenerator.process.DefaultReader;
import com.antonio.docgenerator.process.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;


@SpringBootApplication
    public class DocGeneratorApplication {

    @Autowired
    private Generator generator;

    public static void main(String[] args) {
        SpringApplication.run(DocGeneratorApplication.class, args);
    }

    @Bean
    CommandLineRunner run(DefaultReader defaultReader) {
        return args -> {
            String filePath = "excel-example/DataFromInvoice for example .xlsx"; // Укажи путь к файлу
            try {
                defaultReader.readExcel(filePath);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + e.getMessage());
            }
        };
    }

}
