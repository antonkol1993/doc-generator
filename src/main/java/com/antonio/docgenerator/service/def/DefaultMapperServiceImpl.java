package com.antonio.docgenerator.service.def;

import com.antonio.docgenerator.dto.input.DefaultItem;
import com.antonio.docgenerator.dto.output.LabelLargeBox;
import com.antonio.docgenerator.service.MapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class DefaultMapperServiceImpl implements MapperService<DefaultItem, LabelLargeBox> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMapperServiceImpl.class);
    private final Properties mappingToImage = new Properties();
    private final Properties mappingToValueRUS = new Properties();
    private final Properties mappingToImages = new Properties();

    public DefaultMapperServiceImpl() {
        loadProperties(mappingToImage, "mapping_item-invoice.properties");
        loadProperties(mappingToValueRUS, "mapping_item-RUSvalue.properties");
        loadProperties(mappingToImages, "mapping_item-image.properties");
    }

    private void loadProperties(Properties properties, String fileName) {
        File file = new File("resources/" + fileName);

        if (!file.exists()) {
            logger.error("Файл не найден: {}", file.getAbsolutePath());
            return;
        }

        try (InputStream input = new FileInputStream(file);
             InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            properties.load(reader);
            logger.info("✅ Загружен файл: {}", fileName);
        } catch (IOException e) {
            logger.error("Ошибка загрузки файла {}: {}", fileName, e.getMessage());
        }
    }

    @Override
    public List<List<LabelLargeBox>> map(List<List<DefaultItem>> dataBlocks) {
        List<List<LabelLargeBox>> mappedBlocks = new ArrayList<>();

        for (List<DefaultItem> block : dataBlocks) {
            List<LabelLargeBox> mappedBlock = new ArrayList<>();
            for (DefaultItem item : block) {
                mappedBlock.add(mapItem(item));
            }
            mappedBlocks.add(mappedBlock);
        }

        return mappedBlocks;
    }

    private LabelLargeBox mapItem(DefaultItem item) {
        LabelLargeBox labelBox = new LabelLargeBox();
        labelBox.setItemNo(item.getItemNo());
        labelBox.setSize(item.getSize());
        labelBox.setMarking(item.getMarking());
        labelBox.setQuantityInBox(item.getQuantityInBox());
        labelBox.setOrder(item.getOrder());

        String originalName = item.getOriginalName();
        if (originalName == null || originalName.trim().isEmpty()) {
            logger.warn("⚠ Ошибка: originalName == null или пустой! No [{}]", item.getItemNo());
            return labelBox;
        }
        originalName = originalName.trim();

        String mappedKey = findKeyByValue(mappingToImage, originalName);
        if (mappedKey == null) {
            logger.warn("❌ Значение [{}] не найдено в mapping_item-invoice.properties!", originalName);
            labelBox.setNameRus(item.getAlterNameRus());
            labelBox.setImagePath(item.getAlterImagePath());
            return labelBox;
        } else {
            logger.info("✅ Найден ключ: {}", mappedKey);
            labelBox.setKeyName(mappedKey);

            String nameRus = mappingToValueRUS.getProperty(mappedKey, "");
            labelBox.setNameRus(nameRus);

            String imagePath = mappingToImages.getProperty(mappedKey, "");
            labelBox.setImagePath(imagePath);
            return labelBox;
        }
    }

    private String findKeyByValue(Properties properties, String valueToFind) {
        for (String key : properties.stringPropertyNames()) {
            if (properties.getProperty(key).trim().equals(valueToFind)) {
                return key;
            }
        }
        return null;
    }



}
