package com.antonio.core.generator.generator;

import com.antonio.core.generator.enums.InputType;
import com.antonio.core.generator.enums.OutputType;

import java.io.IOException;

public interface Generator {

    void generate() throws IOException;

    void generate(InputType inputType, OutputType outputType, String inputFilename, String outputFilename) throws IOException;
}
