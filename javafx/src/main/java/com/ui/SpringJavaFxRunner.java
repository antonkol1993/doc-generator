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

import java.net.URL;

@SpringBootApplication(
        exclude = {
                org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration.class,
                org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.class,
                org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration.class
        })
@ComponentScan(basePackages = {
        "com.antonio.interfacefx",         // твой UI слой
        "com.antonio.core",         // твой core
        "com.antonio.persistence.db_init", // где лежит DbInit
        "com.antonio.persistence.repository", // обязательно для репозиториев
        "com.antonio.persistence.entity"   // если есть сущности с @Component
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
        SceneSwitcher.switchScene("/fxml/menu-controller.fxml", "Doc Generator FX");
//        //todo удалить в дальнейшем
//        URL resource = getClass().getClassLoader().getResource("db/changelog/liquibase-changeLog.yaml");
//        System.out.println("Liquibase config path: " + resource);
    }

    @Override
    public void stop() {
        // Завершаем Spring
        context.close();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

//todo удалить в дальнейшем для проверки!!!!
    @PostConstruct
    public void checkLiquibaseChangelog() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL resource = cl.getResource("db/changelog/liquibase-changeLog.yaml");
        System.out.println("Liquibase changelog URL: " + resource);
        if (resource == null) {
            System.out.println("Liquibase changelog NOT found in classpath!");
        } else {
            System.out.println("Liquibase changelog FOUND!");
        }
    }


}
