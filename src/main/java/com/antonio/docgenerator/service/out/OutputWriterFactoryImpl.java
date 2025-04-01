package com.antonio.docgenerator.service.out;

import com.antonio.docgenerator.dto.OutputDto;
import com.antonio.docgenerator.enums.InputType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutputWriterFactoryImpl implements OutputWriterFactory {

    private final OutputWriter outputWriter;

    @Autowired
    public OutputWriterFactoryImpl(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <K extends OutputDto<?>> OutputWriter<K> getWriter(InputType inputType) {
        return switch (inputType) {
            case DEFAULT -> (OutputWriter<K>) outputWriter;
            case HISENER -> (OutputWriter<K>) outputWriter;
            default -> throw new IllegalArgumentException("Unsupported type: " + inputType);
        };
    }
}

