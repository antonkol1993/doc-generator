package com.antonio.docgenerator.service.in;

import com.antonio.docgenerator.dto.InputDto;
import com.antonio.docgenerator.dto.input.DefaultItem;

import java.io.IOException;
import java.util.List;

public interface InputReader<T extends InputDto<?>> {
    List<List<T>> readExcel(String filePath) throws IOException;
}

