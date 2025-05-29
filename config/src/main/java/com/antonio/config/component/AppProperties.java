package com.antonio.config.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private AppProperty database;
    private AppProperty image;
    private AppProperty logo;
    private AppProperty common;

    @Data
    public static class AppProperty {

        private String path;
        private String name;
    }

}
