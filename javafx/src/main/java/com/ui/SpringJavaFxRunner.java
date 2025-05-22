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
        "com.antonio.persistence.entity",   // @Component
        "com.antonio.config"   // Конфиги
})
@EnableJpaRepositories(basePackages = "com.antonio.persistence.repository")
@EntityScan(basePackages = "com.antonio.persistence.entity")

public class SpringJavaFxRunner extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(SpringJavaFxRunner.class)
                .properties("spring.config.location=classpath:/application.yaml")
                .run();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Устанавливаем Spring-контекст и Stage в SceneSwitcher
        SceneSwitcher.setApplicationContext(context);
        SceneSwitcher.setPrimaryStage(primaryStage);

        // Переключаем сцену на главное меню
        SceneSwitcher.switchScene("/fxml/menu-section.fxml", "Doc Generator FX");
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
