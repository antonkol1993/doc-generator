package com.antonio.docgenerator.service.in;

import com.antonio.docgenerator.dto.InputDto;
import com.antonio.docgenerator.enums.InputType;

public interface InputReaderFactory {
    <T extends InputDto<?>> InputReader<T> getReader(InputType inputType);
}

