package com.antonio.core.generator.record;

import com.antonio.core.generator.enums.InputType;
import com.antonio.core.generator.enums.OutputType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class InitParameters {
    private InputType inputType;
    private String inputFileName;
    private OutputType outputType;
    private String outputFileName;
}

