package com.antonio.docgenerator.service.out;

import java.io.IOException;
import java.util.List;

public interface OutputWriter<T> {

    void generateCards(List<List<T>> t) throws IOException;
}
