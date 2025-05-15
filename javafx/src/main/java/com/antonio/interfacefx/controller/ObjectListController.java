package com.antonio.interfacefx.controller;

import com.antonio.persistence.entity.ObjectToGenerator;
import com.antonio.persistence.repository.ObjectToGeneratorRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ObjectListController {


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
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        keyNameColumn.setCellValueFactory(new PropertyValueFactory<>("keyName"));
        engNameColumn.setCellValueFactory(new PropertyValueFactory<>("engName"));
        rusNameColumn.setCellValueFactory(new PropertyValueFactory<>("rusName"));
        producerColumn.setCellValueFactory(new PropertyValueFactory<>("producer"));

        List<ObjectToGenerator> allObjects = repository.findAll();
        objectTable.setItems(FXCollections.observableArrayList(allObjects));
    }
}
