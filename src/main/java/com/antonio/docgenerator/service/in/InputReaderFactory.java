package com.antonio.docgenerator.service.in;

import com.antonio.docgenerator.enums.InputType;

public interface InputReaderFactory {

    public InputReader getReader(InputType inputType); // todo maybe params???


    // приходит deafult , HISENER и т.д.
    // дай
}
