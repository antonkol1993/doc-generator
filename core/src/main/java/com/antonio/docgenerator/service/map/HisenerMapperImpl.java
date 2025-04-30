package com.antonio.docgenerator.service.map;

import com.antonio.docgenerator.dto.InputDto;
import com.antonio.docgenerator.dto.output.LabelLargeBox;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class HisenerMapperImpl implements Mapper<LabelLargeBox> {


    @Override
    public List<List<LabelLargeBox>> map(List<List<InputDto>> t) {
        return List.of();
    }
}
