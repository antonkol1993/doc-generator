package com.antonio.core.generator.service.map;

import com.antonio.core.generator.dto.OutputDto;
import com.antonio.core.generator.enums.InputType;

public interface MapperFactory {
    <K extends OutputDto<?>> Mapper<K> getMapper(InputType inputType);
}

