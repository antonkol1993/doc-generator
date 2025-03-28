package com.antonio.docgenerator;

import com.antonio.docgenerator.dto.input.DefaultItem;
import com.antonio.docgenerator.dto.output.LabelLargeBox;
import com.antonio.docgenerator.service.GeneratorService;
import com.antonio.docgenerator.service.def.DefaultReaderServiceImpl;
import com.antonio.docgenerator.process.Generator;
import com.antonio.docgenerator.service.def.DefaultMapperServiceImpl;
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
    CommandLineRunner run(DefaultReaderServiceImpl defaultReader, DefaultMapperServiceImpl defaultMapperImpl,
                          GeneratorService<LabelLargeBox> generatorService) {
        return args -> {
            List<List<DefaultItem>> defaultList;
            List<List<LabelLargeBox>> preparedItems;
            String filePath = "excel-example/DataFromInvoice for example .xlsx"; // Укажи путь к файлу
            try {
                defaultList = defaultReader.readExcel(filePath);
                preparedItems = defaultMapperImpl.map(defaultList);
                generatorService.generateCards(preparedItems);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + e.getMessage());
            }
        };
    }

}
