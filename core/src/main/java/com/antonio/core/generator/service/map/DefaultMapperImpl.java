package com.antonio.core.generator.service.map;

import com.antonio.core.generator.dto.InputDto;
import com.antonio.core.generator.dto.input.DefaultItem;
import com.antonio.core.generator.dto.output.LabelLargeBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class DefaultMapperImpl implements Mapper<LabelLargeBox> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMapperImpl.class);
    private final Properties mappingToImage = new Properties();
    private final Properties mappingToValueRUS = new Properties();
    private final Properties mappingToImages = new Properties();

    public DefaultMapperImpl() {
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
    public List<List<LabelLargeBox>> map(List<List<InputDto>> dataBlocks) {
        System.out.println(">>> Вызван DeafultMapper: !!!!!!!!!!!!");
        System.out.println(">>> Вызван DeafultMapper: !!!!!!!!!!!!");
        System.out.println(">>> Вызван DeafultMapper: !!!!!!!!!!!!");
        System.out.println(">>> Вызван DeafultMapper: !!!!!!!!!!!!");
        List<List<LabelLargeBox>> mappedBlocks = new ArrayList<>();

        for (List<InputDto> block : dataBlocks) {
            List<LabelLargeBox> mappedBlock = new ArrayList<>();
            for (InputDto it : block) {
                if (it instanceof DefaultItem item) {
                    mappedBlock.add(mapItem(item));
                } else {
                    throw new RuntimeException("are you nuts?");
                }
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

        String originalName = item.getOriginalName().trim();

        String mappedKey = findKeyByValue(mappingToImage, originalName);
        if (mappedKey == null) {
            logger.warn("❌ Значение [{}] не найдено в mapping_item-invoice.properties!", originalName);
            labelBox.setNameRus(item.getAlterNameRus());
            labelBox.setImagePath(item.getAlterImagePath());
        } else {
            logger.info("✅ Найден ключ: {}", mappedKey);
            labelBox.setKeyName(mappedKey);

            String nameRus = mappingToValueRUS.getProperty(mappedKey, "");
            labelBox.setNameRus(nameRus);

            String imagePath = mappingToImages.getProperty(mappedKey, "");
            labelBox.setImagePath(imagePath);
        }
        logger.debug("Результат маппинга: {}", labelBox);
        return labelBox;
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
