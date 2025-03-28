package com.antonio.docgenerator.service;

import java.util.List;

public interface MapperService<T, K> {

    List<List<K>> map(List<List<T>> t);
}
