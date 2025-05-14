package com.antonio.interfacefx.controller;

import com.antonio.interfacefx.config.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MenuController {

    public void onObjects(ActionEvent event) throws IOException {
        switchScene("/fxml/object-view.fxml", event);
    }

    public void onGeneration(ActionEvent event) throws IOException {
        switchScene("/fxml/generation-view.fxml", event);
    }

    public void onExit(ActionEvent event) {
        System.exit(0);
    }

    private void switchScene(String fxmlPath, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(SpringContext::getBean);
        Parent root = loader.load();

        // Получаем текущую сцену из источника события
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
