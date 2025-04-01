package com.antonio.docgenerator.service.in;

import com.antonio.docgenerator.dto.InputDto;
import com.antonio.docgenerator.enums.InputType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InputReaderFactoryImpl implements InputReaderFactory {

    private final DefaultReader defaultReader;
    private final HisenerReader hisenerReader;

    @Autowired
    public InputReaderFactoryImpl(DefaultReader defaultReader,
                                  HisenerReader hisenerReader) {
        this.defaultReader = defaultReader;
        this.hisenerReader = hisenerReader;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends InputDto<?>> InputReader<T> getReader(InputType inputType) {
        return switch (inputType) {
            case DEFAULT -> (InputReader<T>) defaultReader;
            case HISENER -> (InputReader<T>) hisenerReader;
            default -> throw new RuntimeException("Unknown InputType: " + inputType);
        };
    }
}

