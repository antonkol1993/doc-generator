package com.antonio.docgenerator.runner;

import com.antonio.docgenerator.dto.InputDto;
import com.antonio.docgenerator.dto.OutputDto;
import com.antonio.docgenerator.enums.InputType;
import com.antonio.docgenerator.record.InitParameters;
import com.antonio.docgenerator.service.in.InputReader;
import com.antonio.docgenerator.service.in.InputReaderFactory;
import com.antonio.docgenerator.service.map.Mapper;
import com.antonio.docgenerator.service.map.MapperFactory;
import com.antonio.docgenerator.service.out.OutputWriter;
import com.antonio.docgenerator.service.out.OutputWriterFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GeneratorImpl implements Generator {

    private final InitParameters initParameters;
    private final InputReaderFactory inputReaderFactory;
    private final MapperFactory mapperFactory;
    private final OutputWriterFactory outputWriterFactory;

    public GeneratorImpl(InitParameters initParameters, InputReaderFactory inputReaderFactory, MapperFactory mapperFactory, OutputWriterFactory outputWriterFactory) {
        this.initParameters = initParameters;
        this.inputReaderFactory = inputReaderFactory;
        this.mapperFactory = mapperFactory;
        this.outputWriterFactory = outputWriterFactory;
    }

    @Override
    public void generate() throws IOException {
        InputType inputType = initParameters.getInputType();

        // Получаем ридер для нужного типа
        InputReader<? extends InputDto<?>> inputReader = inputReaderFactory.getReader(inputType);

        // Читаем данные
        List<? extends List<? extends InputDto<?>>> inputData = inputReader.readExcel(initParameters.inputFileName());

        // Получаем маппер
        Mapper<InputDto<?>, OutputDto<?>> mapper = mapperFactory.getMapper(inputType);

        // Преобразуем данные
        List<List<OutputDto<?>>> mappedLists = mapper.map((List<List<InputDto<?>>>) inputData);

        // Получаем правильный OutputWriter
        OutputWriter<OutputDto<?>> outputWriter = outputWriterFactory.getWriter(inputType);

        // Генерируем карточки
        outputWriter.generateCards(mappedLists);
    }
}

