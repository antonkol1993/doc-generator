package com.antonio.generator.service.in;

import com.antonio.generator.dto.InputDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HisenerReader implements InputReader {
    @Override
    public List<List<InputDto>> readExcel(String filePath) {
        return List.of();
    }
}
