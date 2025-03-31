package com.antonio.docgenerator.service.in;

import com.antonio.docgenerator.enums.InputType;
import org.springframework.stereotype.Service;

@Service
public class InputReaderFactoryImpl implements InputReaderFactory {


    @Override
    public InputReader getReader(InputType inputType) {
        return switch (inputType) {
            case DEFAULT -> new DefaultReader();
            case HISENER -> new HisenerReader();
            default -> throw new RuntimeException("Unknown InputType: " + inputType);
        };
    }
}
