package com.antonio.core.generator.service.out;

import java.io.IOException;
import java.util.List;

public interface OutputWriter<T> {

    void generateCards(List<List<T>> t, String fileName) throws IOException;
}
