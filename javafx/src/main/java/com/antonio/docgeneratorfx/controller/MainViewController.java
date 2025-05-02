package com.antonio.docgeneratorfx.controller;

import com.antonio.docgenerator.dto.InputDto;
import com.antonio.docgenerator.service.in.InputReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;


@Component
public class MainViewController {

    private final InputReader reader;

    public MainViewController(@Qualifier("defaultReader") InputReader inputReader) {
        this.reader = inputReader;
    }


    @FXML
    private Label statusLabel;

    @FXML
    private Button readButton;

    @FXML
    public void initialize() {
        // этот метод автоматически вызывается после загрузки FXML
        statusLabel.setText("Интерфейс запущен");

        readButton.setOnAction(e -> {
            try {
                List<List<InputDto>> lists = reader.readExcel("excel-example/DataFromInvoice for example .xlsx");
                statusLabel.setText("Файл успешно прочитан");
                for (List<InputDto> list : lists) {
                    for (InputDto dto : list) {
                        System.out.println(dto);
                    }
                }
            } catch (Exception ex) {
                statusLabel.setText("Ошибка: " + ex.getMessage());
            }
        });
    }

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
                data.forEach(list -> list.forEach(System.out::println));
            } catch (Exception e) {
                statusLabel.setText("Ошибка: " + e.getMessage());
            }
        }
    }
}
