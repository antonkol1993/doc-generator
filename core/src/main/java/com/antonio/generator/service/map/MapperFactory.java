package com.antonio.generator.service.map;

import com.antonio.generator.dto.OutputDto;
import com.antonio.generator.enums.InputType;

public interface MapperFactory {
    <K extends OutputDto<?>> Mapper<K> getMapper(InputType inputType);
}

