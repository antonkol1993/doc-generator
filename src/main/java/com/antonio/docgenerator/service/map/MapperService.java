package com.antonio.docgenerator.service.map;

import java.util.List;

public interface MapperService<T, K> {

    List<List<K>> map(List<List<T>> t);
}
