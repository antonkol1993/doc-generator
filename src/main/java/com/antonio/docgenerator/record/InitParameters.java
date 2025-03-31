package com.antonio.docgenerator.record;

import com.antonio.docgenerator.enums.InputType;
import com.antonio.docgenerator.enums.OutputType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("file:./resources/init.properties")
public record InitParameters(@Value("${inputType}") String inputType,
                             @Value("${inputFileName}") String inputFileName,
                             @Value("${outputType}") String outputType,
                             @Value("${outputFileName}") String outputFileName) {

    public InputType getInputType() {
        return InputType.valueOf(inputType);
    }

    public OutputType getOutputType() {
        return OutputType.valueOf(outputType);
    }

}
