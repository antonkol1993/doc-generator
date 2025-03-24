package com.antonio.docgenerator.record;

import com.antonio.docgenerator.enums.InputType;
import com.antonio.docgenerator.enums.OutputType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("file:./resources/init.properties")
public record InitParameters(@Value("${inputStrategy}") String inputStrategy,
                             @Value("${inputFileName}") String inputFileName,
                             @Value("${outputType}") String outputType,
                             @Value("${outputFileName}") String outputFileName) {

    public InputType getInputType() {
        return InputType.valueOf(inputStrategy);
    }

    public OutputType getOutputType() {
        return OutputType.valueOf(outputType);
    }

}
