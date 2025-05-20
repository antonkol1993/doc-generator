package com.antonio.interfacefx.controller;

import com.antonio.core.generator.dto.InputDto;
import com.antonio.core.generator.enums.InputType;
import com.antonio.core.generator.enums.OutputType;
import com.antonio.core.generator.record.InitParameters;
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
    private final InitParameters initParameters;

    public GenerateSectionController(@Qualifier("defaultReader") InputReader reader,
                                     InitParameters initParameters) {
        this.reader = reader;
        this.initParameters = initParameters;
    }

    @FXML
    private Label statusLabel;

    @FXML
    private Button readButton;

    @FXML
    private Button backButton;

    // Кнопка для DEFAULT
    @FXML
    public void onDefault(ActionEvent event) {
        File file = openFileChooser();
        if (file != null) {
            initParameters.setInputType(InputType.DEFAULT);
            initParameters.setInputFileName(file.getAbsolutePath());
            initParameters.setOutputType(OutputType.XLSX);
            initParameters.setOutputFileName("output_default.xlsx");

            statusLabel.setText("Файл выбран: " + file.getName() + " [DEFAULT]");
            SceneSwitcher.switchScene("/fxml/generate-confirmation.fxml", "Подтверждение генерации");
        }
    }

    // Кнопка для HISENER
    @FXML
    public void onHisener(ActionEvent event) {
        File file = openFileChooser();
        if (file != null) {
            initParameters.setInputType(InputType.HISENER);
            initParameters.setInputFileName(file.getAbsolutePath());
            initParameters.setOutputType(OutputType.XLSX);
            initParameters.setOutputFileName("output_hisener.xlsx");

            statusLabel.setText("Файл выбран: " + file.getName() + " [HISENER]");
            SceneSwitcher.switchScene("/fxml/generate-confirmation.fxml", "Подтверждение генерации");
        }
    }

    // Чтение Excel напрямую
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

    // Выбор произвольного файла (для отладки)
    @FXML
    public void onChooseFile(ActionEvent event) {
        File file = openFileChooser();
        if (file != null) {
            statusLabel.setText("Файл: " + file.getName());
            handleFileReading(file);
        }
    }

    @FXML
    public void onBack(ActionEvent event) {
        SceneSwitcher.switchScene("/fxml/menu-section.fxml", "Doc Generator FX");
    }

    private void handleFileReading(File file) {
        try {
            List<List<InputDto>> data = reader.readExcel(file.getAbsolutePath());
            statusLabel.setText("Успешно загружено блоков: " + data.size());
            data.forEach(list -> list.forEach(System.out::println));
        } catch (Exception e) {
            statusLabel.setText("Ошибка: " + e.getMessage());
        }
    }

    private File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите Excel-файл");
        return fileChooser.showOpenDialog(null);
    }
}
