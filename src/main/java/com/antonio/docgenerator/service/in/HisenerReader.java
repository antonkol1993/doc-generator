package com.antonio.docgenerator.service.in;

import com.antonio.docgenerator.dto.input.HisenerItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HisenerReader implements InputReader<HisenerItem> {
    @Override
    public List<List<HisenerItem>> readExcel(String filePath) {
        return List.of();
    }
}
