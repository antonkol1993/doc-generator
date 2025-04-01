package com.antonio.docgenerator.runner;

import com.antonio.docgenerator.dto.input.DefaultItem;
import com.antonio.docgenerator.dto.output.LabelLargeBox;
import com.antonio.docgenerator.record.InitParameters;
import com.antonio.docgenerator.service.in.InputReaderFactory;
import com.antonio.docgenerator.service.map.MapperService;
import com.antonio.docgenerator.service.out.OutputWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GeneratorImpl implements Generator {

    @Autowired
    private InitParameters initParameters;
    @Autowired
    private InputReaderFactory inputReaderFactory;

    //todo Надо видимо переделывать 2 autowired ниже, чтобы был динамическим???
    @Autowired
    private MapperService<DefaultItem,LabelLargeBox> mapperService;
    @Autowired
    private OutputWriter<LabelLargeBox> outputWriter;

    @Override
    public void generate() throws IOException {
        List<List<DefaultItem>> defaultLists = inputReaderFactory.getReader(initParameters.getInputType()).readExcel(initParameters.inputFileName());
        List<List<LabelLargeBox>> mappedLists = mapperService.map(defaultLists);
        outputWriter.generateCards(mappedLists);
    }
}
