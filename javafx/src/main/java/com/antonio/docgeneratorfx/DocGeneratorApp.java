package com.antonio.docgeneratorfx;

import com.antonio.docgenerator.dto.InputDto;
import com.antonio.docgenerator.service.in.InputReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.antonio"})
public class DocGeneratorApp extends Application {

    private GenericApplicationContext context;

    @Qualifier("defaultReader")
    @Autowired // Указываем, какой бин из двух нужно использовать
    private InputReader reader;

    @Override
    public void init() {
        // Загружаем Spring Boot core-модуль
        context = new AnnotationConfigApplicationContext("com.antonio.docgenerator");
        context.getAutowireCapableBeanFactory().autowireBean(this);  // Инжектируем зависимости вручную
    }

    @Override
    public void start(Stage primaryStage) {
        var label = new Label("Интерфейс запущен");
        var button = new Button("Запустить чтение Excel");

        button.setOnAction(e -> {
            try {
                List<List<InputDto>> lists = reader.readExcel("excel-example/DataFromInvoice for example .xlsx");
                label.setText("Файл успешно прочитан");
                for (List<InputDto> list : lists) {
                    for (InputDto dto : list) {
                        System.out.println(dto);
                    }
                }
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

