package com.antonio.docgenerator.service.in;

import com.antonio.docgenerator.dto.input.DefaultItem;
import com.antonio.docgenerator.dto.output.LabelLargeBox;
import com.antonio.docgenerator.enums.InputType;
import com.antonio.docgenerator.service.map.MapperService;
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
