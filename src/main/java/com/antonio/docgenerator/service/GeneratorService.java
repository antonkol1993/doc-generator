package com.antonio.docgenerator.service;

import java.io.IOException;
import java.util.List;

public interface GeneratorService<T> {

    void generateCards(List<List<T>> t) throws IOException;
}
