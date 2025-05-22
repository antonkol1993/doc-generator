package com.run_app;

import com.antonio.core.generator.enums.InputType;
import com.antonio.core.generator.enums.OutputType;
import com.antonio.core.generator.generator.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = {
                "com.antonio.core",
                "com.antonio.config",     // чтобы найти AppProperties
                "com.antonio.core"},       // если сервисы и генераторы там
        exclude = {DataSourceAutoConfiguration.class})
public class DocGeneratorApplication {

    @Autowired
    private Generator generator;

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC driver found!");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver NOT found!");
        }
        SpringApplication.run(DocGeneratorApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            InputType inputType = InputType.DEFAULT;
            OutputType outputType = OutputType.XLSX;
            String inputFileName = "excel-example/DataFromInvoice for example .xlsx";
            String outputFileName = "output.xlsx";

            generator.generate(inputType, outputType, inputFileName, outputFileName);
        };
    }
}
