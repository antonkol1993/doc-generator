package com.antonio.docgenerator.service.map;

import com.antonio.docgenerator.dto.InputDto;
import com.antonio.docgenerator.dto.OutputDto;
import com.antonio.docgenerator.enums.InputType;

public interface MapperFactory {
    <T extends InputDto<?>, K extends OutputDto<?>> Mapper<T, K> getMapper(InputType inputType);
}

