package com.ui;

import com.antonio.interfacefx.util.SceneSwitcher;
import jakarta.annotation.PostConstruct;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.InputStream;
import java.net.URL;

@SpringBootApplication(
        exclude = {
                org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration.class,
                org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.class,
                org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration.class
        })
@ComponentScan(basePackages = {
        "com.antonio.interfacefx",         // UI слой
        "com.antonio.core",         // core
        "com.antonio.persistence.repository", // репозиторий
        "com.antonio.persistence.entity"   // @Component
})
@EnableJpaRepositories(basePackages = "com.antonio.persistence.repository")
@EntityScan(basePackages = "com.antonio.persistence.entity")

public class SpringJavaFxRunner extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {

        // Запускаем Spring Boot и сохраняем контекст
        context = new SpringApplicationBuilder(SpringJavaFxRunner.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Устанавливаем Spring-контекст и Stage в SceneSwitcher
        SceneSwitcher.setApplicationContext(context);
        SceneSwitcher.setPrimaryStage(primaryStage);

        // Переключаем сцену на главное меню
        SceneSwitcher.switchScene("/fxml/menu-section.fxml", "Doc Generator FX");

        //todo удалить потом для проверки!!!!!
        InputStream is = getClass().getClassLoader().getResourceAsStream("db/changelog/liquibase-changeLog.yaml");
        if (is == null) {
            System.out.println("Файл changelog не найден в classpath!");
        } else {
            System.out.println("Файл changelog найден");
        }
    }

    @Override
    public void stop() {
        // Завершаем Spring
        context.close();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
