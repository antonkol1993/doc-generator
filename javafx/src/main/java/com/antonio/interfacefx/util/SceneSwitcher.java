package com.antonio.interfacefx.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

import java.util.logging.Logger;

public class SceneSwitcher {

    private static final Logger logger = Logger.getLogger(SceneSwitcher.class.getName());
    private static ApplicationContext springContext;
    @Setter
    private static Stage primaryStage;

    public static void setApplicationContext(ApplicationContext context) {
        springContext = context;
    }

    public static void switchScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlPath));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle(title);
            primaryStage.show();
        } catch (Exception e) {
            // Логируем ошибку с понятным сообщением
            logger.severe("Ошибка при загрузке сцены: " + fxmlPath + ". Сообщение ошибки: " + e.getMessage());
        }
    }
}

