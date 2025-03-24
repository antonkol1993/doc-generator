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
        String message = String.format("Input: %s; strategy: %s; output: %s; type: %s",
                initParameters.inputFileName(), initParameters.getInputType(), initParameters.outputFileName(), initParameters.getOutputType());
        System.out.println(message);

        InputStrategy strategy = inputStrategyFactory.getStrategy(initParameters.getInputType());

        somedata = strategy.justDoIt(initParameters.inputFileName());

        //logger
        outputStrategy.doOutput(somedata)

        // TODO
        System.out.println("Finished!");
    }
}
