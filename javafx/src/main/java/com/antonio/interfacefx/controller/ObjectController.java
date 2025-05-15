package com.antonio.interfacefx.controller;

import com.antonio.interfacefx.util.SceneSwitcher;
import com.antonio.persistence.repository.ObjectToGeneratorRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectController {

    private final ObjectToGeneratorRepository repository;

    @FXML
    private Button createObjectButton;

    @FXML
    private Button listOfObjects;

    @FXML
    private Button backButton;

    // Обработчик для кнопки "Создать объект"
    @FXML
    public void onCreateObject(ActionEvent event) {
        System.out.println("Объект создан!");
    }

    // Обработчик для кнопки "Создать объект"
    @FXML
    public void onListOfObjects(ActionEvent event) {
        SceneSwitcher.openInNewWindow("/fxml/list-of-objects.fxml", "Список объектов");
        System.out.println("Список объектов!");
    }

    // Обработчик кнопки "Назад"
    @FXML
    public void onBack(ActionEvent event) {
        SceneSwitcher.switchScene("/fxml/menu-view.fxml", "Doc Generator FX");
    }
}
