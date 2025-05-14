package com.antonio.interfacefx.controller;

import com.antonio.interfacefx.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component
public class ObjectController {

    @FXML
    private Button createObjectButton;

    @FXML
    private Button backButton;

    // Обработчик для кнопки "Создать объект"
    @FXML
    public void onCreateObject(ActionEvent event) {
        System.out.println("Объект создан!");
    }

    // Обработчик кнопки "Назад"
    @FXML
    public void onBack(ActionEvent event) {
        SceneSwitcher.switchScene("/fxml/menu-view.fxml", "Doc Generator FX");
    }
}
