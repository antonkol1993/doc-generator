package com.antonio.interfacefx.controller;

import com.antonio.core.generator.dto.InputDto;
import com.antonio.core.generator.service.in.InputReader;
import com.antonio.interfacefx.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class GenerateSectionController {

    private final InputReader reader;

    public GenerateSectionController(@Qualifier("defaultReader") InputReader inputReader) {
        this.reader = inputReader;
    }

    @FXML
    private Label statusLabel;

    @FXML
    private Button readButton;

    @FXML
    private Button backButton;


    // Обработчик кнопки "Чтение Excel"
    @FXML
    public void onRead(ActionEvent event) {
        try {
            List<List<InputDto>> lists = reader.readExcel("excel-example/DataFromInvoice for example .xlsx");
            statusLabel.setText("Файл успешно прочитан");
            lists.forEach(list -> list.forEach(System.out::println));
        } catch (Exception ex) {
            statusLabel.setText("Ошибка: " + ex.getMessage());
        }
    }

    // Обработчик кнопки "Выбрать файл"
    @FXML
    public void onChooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите Excel-файл");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            statusLabel.setText("Файл: " + file.getName());
            handleFileReading(file);
        }
    }

    // Обработчик кнопки "Назад"
    @FXML
    public void onBack(ActionEvent event) {
        SceneSwitcher.switchScene("/fxml/menu-section.fxml", "Doc Generator FX");
    }

    // Чтение файла
    private void handleFileReading(File file) {
        try {
            List<List<InputDto>> data = reader.readExcel(file.getAbsolutePath());
            statusLabel.setText("Успешно загружено блоков: " + data.size());
            data.forEach(list -> list.forEach(System.out::println));
        } catch (Exception e) {
            statusLabel.setText("Ошибка: " + e.getMessage());
        }
    }
}
