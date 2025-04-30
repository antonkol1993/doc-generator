package com.antonio.docgeneratorfx.controller;

import com.antonio.docgenerator.dto.InputDto;
import com.antonio.docgenerator.service.in.InputReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor

public class MainViewController {
    private final InputReader reader;

    @FXML
    private Label statusLabel;

    @FXML
    public void onChooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите Excel-файл");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            statusLabel.setText("Файл: " + file.getName());
            try {
                List<List<InputDto>> data = reader.readExcel(file.getAbsolutePath());
                statusLabel.setText("Успешно загружено блоков: " + data.size());
            } catch (Exception e) {
                statusLabel.setText("Ошибка: " + e.getMessage());
            }
        }
    }
}
