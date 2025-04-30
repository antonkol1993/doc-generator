package com.antonio.docgenerator.service.out;

import com.antonio.docgenerator.dto.OutputDto;
import com.antonio.docgenerator.enums.OutputType;

public interface OutputWriterFactory {
    <K extends OutputDto<?>> OutputWriter<K> getWriter(OutputType outputType, String fileName);
}

