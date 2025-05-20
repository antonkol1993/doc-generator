package com.antonio.interfacefx.controller;

import com.antonio.interfacefx.config.AppProperties;
import com.antonio.interfacefx.util.SceneSwitcher;
import com.antonio.persistence.entity.ObjectToGenerator;
import com.antonio.persistence.repository.ObjectToGeneratorRepository;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
@RequiredArgsConstructor
public class ObjectFormController {

    private final ObjectToGeneratorRepository repository;
    private final AppProperties appProperties;

    @FXML
    private VBox rootPane;

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
    public void initialize() {
        imagePathField.setOnDragOver(event -> {
            if (event.getGestureSource() != imagePathField && event.getDragboard().hasFiles()) {
                boolean hasImage = event.getDragboard().getFiles().stream()
                        .anyMatch(this::isImageFile);
                if (hasImage) {
                    event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
                }
            }
            event.consume();
        });

        imagePathField.setOnDragEntered(event -> {
            if (event.getGestureSource() != imagePathField && event.getDragboard().hasFiles()) {
                boolean hasImage = event.getDragboard().getFiles().stream()
                        .anyMatch(this::isImageFile);
                if (hasImage) {
                    imagePathField.setStyle("-fx-background-color: lightgreen;");
                }
            }
            event.consume();
        });

        imagePathField.setOnDragExited(event -> {
            imagePathField.setStyle(""); // сброс стиля
            event.consume();
        });

        imagePathField.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                for (File file : db.getFiles()) {
                    if (isImageFile(file)) {
                        String relativePath = copyImageToStorage(file.getAbsolutePath());
                        if (relativePath != null) {
                            imagePathField.setText(relativePath);
                            success = true;
                        }
                        break; // берем только первый файл
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")
                || name.endsWith(".gif") || name.endsWith(".bmp");
    }


    @FXML
    public void onSave() {
        ObjectToGenerator object = new ObjectToGenerator();
        object.setKeyName(emptyToNull(keyNameField.getText()));
        object.setEngName(emptyToNull(engNameField.getText()));
        object.setRusName(emptyToNull(rusNameField.getText()));
        object.setTo1C8name(emptyToNull(to1C8NameField.getText()));
        object.setTn_ved_code(emptyToNull(tnVedCodeField.getText()));
        object.setProducer(emptyToNull(producerField.getText()));

        // Уже скопированная картинка в imagePathField, просто сохраняем
        object.setImagePath(emptyToNull(imagePathField.getText()));

        repository.save(object);

        Stage stage = (Stage) keyNameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(keyNameField.getScene().getWindow());

        if (selectedFile != null) {
            String relativePath = copyImageToStorage(selectedFile.getAbsolutePath());
            if (relativePath != null) {
                imagePathField.setText(relativePath);
            }
        }
    }

    private String emptyToNull(String text) {
        return (text == null || text.trim().isEmpty()) ? null : text.trim();
    }

    private String copyImageToStorage(String originalPath) {
        if (originalPath == null || originalPath.trim().isEmpty()) {
            return null;
        }

        try {
            Path source = Paths.get(originalPath);
            String fileName = source.getFileName().toString();

            String baseDir = appProperties.getImagePath(); // например, storage/images/metalware
            Path destination = Paths.get(baseDir, fileName);
            Files.createDirectories(destination.getParent());

            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

            // Возвращаем только имя файла, без пути
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}

