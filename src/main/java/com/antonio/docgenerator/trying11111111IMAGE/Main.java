package com.antonio.docgenerator.trying11111111IMAGE;

import java.io.IOException;

public  class Main {
    public static void main(String[] args) {
        try {
            new SimpleCardGenerator().generateCard("generated-card.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
