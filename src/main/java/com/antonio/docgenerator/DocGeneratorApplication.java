package com.antonio.docgenerator;

import com.antonio.docgenerator.dto.input.DefaultItem;
import com.antonio.docgenerator.process.DefaultReader;
import com.antonio.docgenerator.process.Generator;
import com.antonio.docgenerator.process.DefaultMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;


@SpringBootApplication
    public class DocGeneratorApplication {

    @Autowired
    private Generator generator;

    public static void main(String[] args) {
        SpringApplication.run(DocGeneratorApplication.class, args);
    }

    @Bean
    CommandLineRunner run(DefaultReader defaultReader, DefaultMapperImpl defaultMapperImpl) {
        return args -> {
            List<List<DefaultItem>> defaultList;
            String filePath = "excel-example/DataFromInvoice for example .xlsx"; // Укажи путь к файлу
            try {
                defaultList = defaultReader.readExcel(filePath);
                defaultMapperImpl.map(defaultList);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + e.getMessage());
            }
        };
    }

}
