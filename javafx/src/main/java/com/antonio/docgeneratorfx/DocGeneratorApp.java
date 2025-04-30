package com.antonio.docgeneratorfx;

import com.antonio.docgenerator.service.in.InputReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.antonio"})
public class DocGeneratorApp extends Application {

    private GenericApplicationContext context;

    @Override
    public void init() {
        // Загружаем Spring Boot core-модуль
        context = new AnnotationConfigApplicationContext("com.antonio.docgenerator");
    }

    @Override
    public void start(Stage primaryStage) {
        InputReader reader = context.getBean(InputReader.class);

        var label = new Label("Интерфейс запущен");
        var button = new Button("Запустить чтение Excel");

        button.setOnAction(e -> {
            try {
                reader.readExcel("путь_к_файлу.xlsx");
                label.setText("Файл успешно прочитан");
            } catch (Exception ex) {
                label.setText("Ошибка: " + ex.getMessage());
            }
        });

        var layout = new VBox(10, label, button);
        var scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Doc Generator FX");
        primaryStage.show();
    }

    @Override
    public void stop() {
        context.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

