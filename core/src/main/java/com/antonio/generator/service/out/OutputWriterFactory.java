package com.antonio.generator.service.out;

import com.antonio.generator.dto.OutputDto;
import com.antonio.generator.enums.OutputType;

public interface OutputWriterFactory {
    <K extends OutputDto<?>> OutputWriter<K> getWriter(OutputType outputType, String fileName);
}

