package com.antonio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class CommonPropertiesLargeBox {

    private final Properties properties = new Properties();

    public CommonPropertiesLargeBox(@Value("file:${app.storage.path}/common-large-box-excel.properties") Resource resource) {
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось загрузить common-large-box-excel.properties", e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key, "");
    }
}
