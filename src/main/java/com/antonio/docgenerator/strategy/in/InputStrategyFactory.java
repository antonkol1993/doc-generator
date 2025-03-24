package com.antonio.docgenerator.strategy.in;

import com.antonio.docgenerator.enums.InputType;

public interface InputStrategyFactory {

    public InputStrategy getStrategy(InputType inputStrategy); // todo maybe params???
}
