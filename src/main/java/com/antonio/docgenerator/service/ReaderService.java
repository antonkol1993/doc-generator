package com.antonio.docgenerator.service;

import java.io.IOException;
import java.util.List;

public interface ReaderService<T>{

    List<List<T>> readExcel(String filePath) throws IOException ;
}
