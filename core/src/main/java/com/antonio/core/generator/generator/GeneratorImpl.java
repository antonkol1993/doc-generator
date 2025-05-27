package com.antonio.core.generator.generator;

import com.antonio.core.generator.service.map.Mapper;
import com.antonio.core.generator.service.map.MapperFactory;
import com.antonio.core.generator.service.out.OutputWriter;
import com.antonio.core.generator.dto.InputDto;
import com.antonio.core.generator.dto.OutputDto;
import com.antonio.core.generator.enums.InputType;
import com.antonio.core.generator.enums.OutputType;
import com.antonio.core.generator.record.InitParameters;
import com.antonio.core.generator.service.in.InputReader;
import com.antonio.core.generator.service.in.InputReaderFactory;
import com.antonio.core.generator.service.out.OutputWriterFactory;
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
        OutputType outputType = initParameters.getOutputType();
        String inputFilename = initParameters.getInputFileName();
        String outputFilename = initParameters.getOutputFileName();

        generate(inputType, outputType, inputFilename, outputFilename);

    }

    public void generate(InputType inputType, OutputType outputType, String inputFilename, String outputFilename) throws IOException {
        // Получаем ридер для нужного типа
        InputReader inputReader = inputReaderFactory.getReader(inputType);

        // Читаем данные
        List<List<InputDto>> inputData = inputReader.readExcel(inputFilename);

        // Получаем маппер
        Mapper<OutputDto<?>> mapper = mapperFactory.getMapper(inputType);

        // Преобразуем данные
        List<List<OutputDto<?>>> mappedLists = mapper.map(inputData);

        // Получаем правильный OutputWriter
        OutputWriter<OutputDto<?>> outputWriter = outputWriterFactory.getWriter(outputType, outputFilename);

        // Генерируем карточки
        outputWriter.generateCards(mappedLists, outputFilename);
    }
}

