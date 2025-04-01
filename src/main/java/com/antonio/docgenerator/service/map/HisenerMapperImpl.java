package com.antonio.docgenerator.service.map;

import com.antonio.docgenerator.dto.input.HisenerItem;
import com.antonio.docgenerator.dto.output.LabelLargeBox;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class HisenerMapperImpl implements Mapper<HisenerItem, LabelLargeBox> {


    @Override
    public List<List<LabelLargeBox>> map(List<List<HisenerItem>> t) {
        return List.of();
    }
}
