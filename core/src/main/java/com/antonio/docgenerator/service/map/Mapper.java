package com.antonio.docgenerator.service.map;

import com.antonio.docgenerator.dto.InputDto;
import com.antonio.docgenerator.dto.OutputDto;

import java.util.List;

public interface Mapper<K extends OutputDto<?>> {

    List<List<K>> map(List<List<InputDto>> t);
}
