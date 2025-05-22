package com.antonio.core.generator.service.map;

import com.antonio.config.component.AppProperties;
import com.antonio.core.generator.dto.InputDto;
import com.antonio.core.generator.dto.input.DefaultItem;
import com.antonio.core.generator.dto.output.LabelLargeBox;
import com.antonio.persistence.entity.ObjectToGenerator;
import com.antonio.persistence.repository.ObjectToGeneratorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.antonio.core.util.AnsiColors.*;

@Service
@RequiredArgsConstructor
public class DefaultMapperImpl implements Mapper<LabelLargeBox> {

    private final AppProperties appProperties;
    private final ObjectToGeneratorRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(DefaultMapperImpl.class);

    @Override
    public List<List<LabelLargeBox>> map(List<List<InputDto>> dataBlocks) {
        logger.info("📂 начало обработки " + color(this.getClass().getSimpleName(), YELLOW));
        List<List<LabelLargeBox>> mappedBlocks = new ArrayList<>();

        for (List<InputDto> block : dataBlocks) {
            List<LabelLargeBox> mappedBlock = new ArrayList<>();
            for (InputDto it : block) {
                if (it instanceof DefaultItem item) {
                    mappedBlock.add(mapItem(item));
                } else {
                    throw new RuntimeException("❌ Неподдерживаемый тип входных данных: " + it.getClass());
                }
            }
            mappedBlocks.add(mappedBlock);
        }
        logger.info("📂 обработка завершена " + color(this.getClass().getSimpleName(), YELLOW));
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
        ObjectToGenerator object = repository.findByEngName(originalName).orElse(null);

        String imageFileName;

        if (object == null) {
            logger.warn(color(" ‼️ Объект с engName = [" + originalName + "] не найден в БД!", ORANGE));
            labelBox.setNameRus(item.getAlterNameRus());
            imageFileName = item.getAlterImageName();
        } else {
            logger.info(color("✅ Найден объект по engName: " + originalName, RESET));
            labelBox.setKeyName(object.getKeyName());
            labelBox.setNameRus(object.getRusName());
            imageFileName = object.getImagePath();
        }

        labelBox.setImageName(imageFileName); // <--- сохраняем ТОЛЬКО название файла
        logImageCheck(imageFileName);         // <--- проверка по имени
        logger.debug(color("📦 Результат маппинга: " + labelBox, CYAN));
        return labelBox;
    }

    private void logImageCheck(String imageFileName) {
        String basePath = appProperties.getImage().getPath();
        Path fullPath = Path.of(basePath, imageFileName);

        if (Files.exists(fullPath)) {
            logger.info(color("🖼 Найдена картинка: " + imageFileName, RESET));
        } else {
            logger.warn(color("⚠️ Картинка не найдена: " + imageFileName, ORANGE));
        }
    }

}
