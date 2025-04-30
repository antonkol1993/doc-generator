package com.antonio.docgenerator.service.map;

import com.antonio.docgenerator.dto.OutputDto;
import com.antonio.docgenerator.enums.InputType;

public interface MapperFactory {
    <K extends OutputDto<?>> Mapper<K> getMapper(InputType inputType);
}

