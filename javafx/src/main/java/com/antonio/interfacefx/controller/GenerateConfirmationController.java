package com.antonio.interfacefx.controller;

import com.antonio.core.generator.generator.Generator;
import com.antonio.core.generator.record.InitParameters;
import com.antonio.interfacefx.util.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import org.springframework.stereotype.Component;

@Component
public class GenerateConfirmationController {

    private final Generator generator;
    private final InitParameters initParameters;

    public GenerateConfirmationController(Generator generator, InitParameters initParameters) {
        this.generator = generator;
        this.initParameters = initParameters;
    }

    @FXML
    private Label infoLabel;

    @FXML
    private Label resultLabel;

    @FXML
    public void initialize() {
        String info = String.format("""
                        Подтвердите генерацию:
                        Тип ввода: %s
                        Файл ввода: %s
                        Тип вывода: %s
                        Файл вывода: %s
                        """,
                initParameters.getInputType(),
                initParameters.getInputFileName(),
                initParameters.getOutputType(),
                initParameters.getOutputFileName());

        infoLabel.setText(info);
    }

    @FXML
    public void onGenerate(ActionEvent event) {
        try {
            generator.generate();
            resultLabel.setText("✅ Генерация завершена успешно.");
        } catch (Exception e) {
            resultLabel.setText("❌ Ошибка генерации: " + e.getMessage());
        }
    }

    @FXML
    public void onBack(ActionEvent event) {
        SceneSwitcher.switchScene("/fxml/generate-section.fxml", "Назад к выбору файла");
    }
}
