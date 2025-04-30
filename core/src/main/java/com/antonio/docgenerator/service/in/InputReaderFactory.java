package com.antonio.docgenerator.service.in;

import com.antonio.docgenerator.enums.InputType;

public interface InputReaderFactory {
    InputReader getReader(InputType inputType);
}

