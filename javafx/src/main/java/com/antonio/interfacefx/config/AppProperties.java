package com.antonio.interfacefx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
    @Value("${app.storage.path}")
    private String storagePath;

    public String getStoragePath() {
        return storagePath;
    }
}
