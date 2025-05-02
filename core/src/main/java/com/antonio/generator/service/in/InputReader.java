package com.antonio.generator.service.in;

import com.antonio.generator.dto.InputDto;

import java.io.IOException;
import java.util.List;

public interface InputReader {
    List<List<InputDto>> readExcel(String filePath) throws IOException;
}

