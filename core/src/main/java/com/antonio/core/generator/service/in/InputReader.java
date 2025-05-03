package com.antonio.core.generator.service.in;

import com.antonio.core.generator.dto.InputDto;

import java.io.IOException;
import java.util.List;

public interface InputReader {
    List<List<InputDto>> readExcel(String filePath) throws IOException;
}

