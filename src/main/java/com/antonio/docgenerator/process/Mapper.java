package com.antonio.docgenerator.process;

import java.util.List;

public interface Mapper<T, K> {

    List<List<K>> map(List<List<T>> t);
}
