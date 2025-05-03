package com.antonio.core.generator.service.in;

import com.antonio.core.generator.enums.InputType;

public interface InputReaderFactory {
    InputReader getReader(InputType inputType);
}

