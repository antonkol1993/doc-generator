package com.antonio.config.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Database database;
    private Image image;
    private Logo logo;
    private Common common;

    @Data
    public static class Database {
        private String path;
        private String name;
    }

    @Data
    public static class Image {
        private String path;
    }

    @Data
    public static class Logo {
        private String path;
    }

    @Data
    public static class Common {
        private String path;
    }
}
