package com.antonio.generator.record;

import com.antonio.generator.enums.InputType;
import com.antonio.generator.enums.OutputType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("file:./resources/init.properties")
public record InitParameters(@Value("${inputType}") String inputType,
                             @Value("${inputFileName}") String inputFileName,
                             @Value("${inputType}") String mapperType,
                             @Value("${outputType}") String outputType,
                             @Value("${outputFileName}") String outputFileName) {

    public InputType getInputType() {
        return InputType.valueOf(inputType);
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public OutputType getOutputType() {
        return OutputType.valueOf(outputType);
    }

    public String getOutputFileName() {
        return outputFileName;
    }

}
