package com.antonio.generator.service.map;

import com.antonio.generator.dto.InputDto;
import com.antonio.generator.dto.OutputDto;

import java.util.List;

public interface Mapper<K extends OutputDto<?>> {

    List<List<K>> map(List<List<InputDto>> t);
}
