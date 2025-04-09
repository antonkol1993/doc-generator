package com.antonio.docgenerator.service.in;

import com.antonio.docgenerator.dto.InputDto;

import java.io.IOException;
import java.util.List;

public interface InputReader {
    List<List<InputDto>> readExcel(String filePath) throws IOException;
}

