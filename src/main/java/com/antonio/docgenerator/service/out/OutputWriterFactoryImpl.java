package com.antonio.docgenerator.service.out;

import com.antonio.docgenerator.dto.OutputDto;
import com.antonio.docgenerator.dto.output.LabelLargeBox;
import com.antonio.docgenerator.enums.OutputType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OutputWriterFactoryImpl implements OutputWriterFactory {

    private final OutputWriterXLSXImpl outputWriterXLSX;
//    private final OutputWriterPDFImpl outputWriterPDF;

    @Autowired
    public OutputWriterFactoryImpl(OutputWriterXLSXImpl outputWriterXLSX) {
        this.outputWriterXLSX = outputWriterXLSX;
    }

    @Override
    public <K extends OutputDto<?>> OutputWriter<K> getWriter(OutputType outputType, String outputName) {
        return switch (outputType) {
            case XLSX -> (OutputWriter<K>) (OutputWriter<LabelLargeBox>) outputWriterXLSX;
//            case PDF -> (OutputWriter<K>) (OutputWriter<LabelLargeBox>) outputWriterPDF;
            default -> throw new IllegalArgumentException("Unsupported type: " + outputType);
        };
    }
}

