package com.antonio.core.generator.service.in;

import com.antonio.core.generator.enums.InputType;
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
    public InputReader getReader(InputType inputType) {
        return switch (inputType) {
            case DEFAULT -> defaultReader;
            case HISENER -> hisenerReader;
            default -> throw new RuntimeException("Неизвестный входящий тип: " + inputType);
        };
    }
}

