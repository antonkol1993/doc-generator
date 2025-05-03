package com.antonio.core.generator.service.map;

import com.antonio.core.generator.dto.InputDto;
import com.antonio.core.generator.dto.OutputDto;

import java.util.List;

public interface Mapper<K extends OutputDto<?>> {

    List<List<K>> map(List<List<InputDto>> t);
}
