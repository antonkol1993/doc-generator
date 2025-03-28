package com.antonio.docgenerator.process;

import com.antonio.docgenerator.dto.input.DefaultItem;

import java.io.IOException;
import java.util.List;

public interface Reader <T>{

    List<List<T>> readExcel(String filePath) throws IOException ;
}
