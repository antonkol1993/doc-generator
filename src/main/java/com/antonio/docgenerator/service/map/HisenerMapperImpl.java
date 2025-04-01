package com.antonio.docgenerator.service.map;

import com.antonio.docgenerator.dto.input.DefaultItem;
import com.antonio.docgenerator.dto.input.HisenerItem;
import com.antonio.docgenerator.dto.output.LabelLargeBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class HisenerMapperImpl implements Mapper<HisenerItem, LabelLargeBox> {


    @Override
    public List<List<LabelLargeBox>> map(List<List<HisenerItem>> t) {
        return List.of();
    }
}
