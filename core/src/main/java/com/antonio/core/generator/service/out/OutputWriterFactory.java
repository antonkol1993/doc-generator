package com.antonio.core.generator.service.out;

import com.antonio.core.generator.dto.OutputDto;
import com.antonio.core.generator.enums.OutputType;

public interface OutputWriterFactory {
    <K extends OutputDto<?>> OutputWriter<K> getWriter(OutputType outputType, String fileName);
}

