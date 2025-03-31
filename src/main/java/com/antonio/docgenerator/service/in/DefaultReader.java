package com.antonio.docgenerator.service.in;


import com.antonio.docgenerator.dto.input.DefaultItem;

import java.io.IOException;
import java.util.List;

public class DefaultReader implements InputReader {

    @Override
    public List<List<DefaultItem>> readExcel(String filePath) throws IOException {
        return List.of();
    }
}
