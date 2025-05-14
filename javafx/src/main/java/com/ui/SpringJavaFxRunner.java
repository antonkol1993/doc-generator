package com.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
        // Загружаем FXML и внедряем зависимости через Spring
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-view.fxml"));
        loader.setControllerFactory(context::getBean); // даёт Spring управлять контроллером
        Parent root = loader.load();

        // Отображаем сцену
        primaryStage.setTitle("Doc Generator FX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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
