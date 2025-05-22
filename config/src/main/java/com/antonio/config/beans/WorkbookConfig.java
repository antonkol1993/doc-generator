//package com.antonio.config.beans;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//
//@Configuration
//public class WorkbookConfig {
//
//    @PostConstruct
//    public void init() {
//        System.out.println("✅ WorkbookConfig инициализирован");
//    }
//
//    @Bean
//    public Workbook workbook() {
//        return new XSSFWorkbook();
//    }
//}
