package com.antonio.docgeneratorfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringJavaFxRunner extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        // Запускаем Spring Boot и сохраняем контекст
        context = new SpringApplicationBuilder(JavaFxApp.class).run();
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
        launch(args);
    }
}
