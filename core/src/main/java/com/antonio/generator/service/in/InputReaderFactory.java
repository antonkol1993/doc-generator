package com.antonio.generator.service.in;

import com.antonio.generator.enums.InputType;

public interface InputReaderFactory {
    InputReader getReader(InputType inputType);
}

