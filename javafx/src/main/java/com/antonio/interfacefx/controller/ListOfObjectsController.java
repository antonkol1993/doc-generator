package com.antonio.interfacefx.controller;

import com.antonio.persistence.entity.ObjectToGenerator;
import com.antonio.persistence.repository.ObjectToGeneratorRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListOfObjectsController {


    private final ObjectToGeneratorRepository repository;

    @FXML
    private TableView<ObjectToGenerator> objectTable;
    @FXML
    private TableColumn<ObjectToGenerator, Long> idColumn;
    @FXML
    private TableColumn<ObjectToGenerator, String> keyNameColumn;
    @FXML
    private TableColumn<ObjectToGenerator, String> engNameColumn;
    @FXML
    private TableColumn<ObjectToGenerator, String> rusNameColumn;
    @FXML
    private TableColumn<ObjectToGenerator, String> producerColumn;
    @FXML
    private TableColumn<ObjectToGenerator, String> to1C8NameColumn;
    @FXML
    private TableColumn<ObjectToGenerator, String> imageNameColumn;
    @FXML
    private TableColumn<ObjectToGenerator, String> tnVedCodeColumn;


    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        keyNameColumn.setCellValueFactory(new PropertyValueFactory<>("keyName"));
        engNameColumn.setCellValueFactory(new PropertyValueFactory<>("engName"));
        rusNameColumn.setCellValueFactory(new PropertyValueFactory<>("rusName"));
        to1C8NameColumn.setCellValueFactory(new PropertyValueFactory<>("to1C8name")); // имя должно соответствовать геттеру
        imageNameColumn.setCellValueFactory(new PropertyValueFactory<>("imageName"));
        tnVedCodeColumn.setCellValueFactory(new PropertyValueFactory<>("tn_ved_code"));
        producerColumn.setCellValueFactory(new PropertyValueFactory<>("producer"));

        List<ObjectToGenerator> allObjects = repository.findAll();
        objectTable.setItems(FXCollections.observableArrayList(allObjects));
    }

}
