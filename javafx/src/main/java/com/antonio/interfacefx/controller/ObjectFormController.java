package com.antonio.interfacefx.controller;

import com.antonio.persistence.entity.ObjectToGenerator;
import com.antonio.persistence.repository.ObjectToGeneratorRepository;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectFormController {

    private final ObjectToGeneratorRepository repository;

    @FXML
    private TextField keyNameField;
    @FXML
    private TextField engNameField;
    @FXML
    private TextField rusNameField;
    @FXML
    private TextField to1C8NameField;
    @FXML
    private TextField imagePathField;
    @FXML
    private TextField tnVedCodeField;
    @FXML
    private TextField producerField;

    @FXML
    public void onSave() {
        ObjectToGenerator object = new ObjectToGenerator();
        object.setKeyName(emptyToNull(keyNameField.getText()));
        object.setEngName(emptyToNull(engNameField.getText()));
        object.setRusName(emptyToNull(rusNameField.getText()));
        object.setTo1C8name(emptyToNull(to1C8NameField.getText()));
        object.setImagePath(emptyToNull(imagePathField.getText()));
        object.setTn_ved_code(emptyToNull(tnVedCodeField.getText()));
        object.setProducer(emptyToNull(producerField.getText()));

        repository.save(object);

        Stage stage = (Stage) keyNameField.getScene().getWindow();
        stage.close();
    }

    private String emptyToNull(String text) {
        return (text == null || text.trim().isEmpty()) ? null : text.trim();
    }
}
