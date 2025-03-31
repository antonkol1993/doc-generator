package com.antonio.docgenerator.runner;

import com.antonio.docgenerator.dto.input.DefaultItem;
import com.antonio.docgenerator.dto.output.LabelLargeBox;
import com.antonio.docgenerator.record.InitParameters;
import com.antonio.docgenerator.service.in.InputReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GeneratorImpl implements Generator {

    @Autowired
    private InitParameters initParameters;
    //
    @Autowired
    private InputReaderFactory inputReaderFactory;
    //
//    private OutputStrategy outputStrategy;
    List<List<DefaultItem>> defaultList;
    List<List<LabelLargeBox>> preparedItems;
    String filePath = "excel-example/DataFromInvoice for example .xlsx"; // Укажи путь к файлу
            try

    {
        defaultList = defaultReader.readExcel(filePath);
        preparedItems = defaultMapperImpl.map(defaultList);
        generatorService.generateCards(preparedItems);
    } catch(
    IOException e)

    {
        System.err.println("Ошибка при чтении файла: " + e.getMessage());
    }

    @Override
    public void generate() {
        List<List<DefaultItem>> lists = inputReaderFactory.getReader(initParameters.getInputType()).readExcel(initParameters.inputFileName());
    }
}
