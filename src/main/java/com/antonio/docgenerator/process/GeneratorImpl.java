package com.antonio.docgenerator.process;

import com.antonio.docgenerator.record.InitParameters;
import com.antonio.docgenerator.strategy.in.InputStrategy;
import com.antonio.docgenerator.strategy.in.InputStrategyFactory;
import com.antonio.docgenerator.strategy.out.OutputStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneratorImpl implements Generator {

    @Autowired
    private InitParameters initParameters;

    @Autowired
    private InputStrategyFactory inputStrategyFactory;

    private OutputStrategy outputStrategy;

    @Override
    public void generate() {

    }
}
