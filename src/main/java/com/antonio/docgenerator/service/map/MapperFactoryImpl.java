package com.antonio.docgenerator.service.map;

import com.antonio.docgenerator.dto.InputDto;
import com.antonio.docgenerator.dto.OutputDto;
import com.antonio.docgenerator.enums.InputType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperFactoryImpl implements MapperFactory {

    private final DefaultMapperImpl defaultMapper;
    private final HisenerMapperImpl hisenerMapper;

    @Autowired
    public MapperFactoryImpl(DefaultMapperImpl defaultMapper, HisenerMapperImpl hisenerMapper) {
        this.defaultMapper = defaultMapper;
        this.hisenerMapper = hisenerMapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <K extends OutputDto<?>> Mapper<K> getMapper(InputType inputType) {
        return switch (inputType) {
            case DEFAULT -> (Mapper<K>) defaultMapper;
            case HISENER -> (Mapper<K>) hisenerMapper;
            default -> throw new IllegalArgumentException("Неподдерживаемый тип: " + inputType);
        };
    }
}

