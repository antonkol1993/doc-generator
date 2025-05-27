package com.antonio.core.generator.for_test_jpeg;

public class Main {
    public static void main(String[] args) throws Exception {
        LabelData data = new LabelData();
        LabelGenerator.generateLabel(data, "label_output.png");
    }
}

