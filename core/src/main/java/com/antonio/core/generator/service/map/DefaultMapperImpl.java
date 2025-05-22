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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMapperImpl implements Mapper<LabelLargeBox> {

    private final AppProperties appProperties;
    private final ObjectToGeneratorRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(DefaultMapperImpl.class);


    @Override
    public List<List<LabelLargeBox>> map(List<List<InputDto>> dataBlocks) {
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

        // ⛏ ищем по engName
        ObjectToGenerator object = repository.findByEngName(originalName).orElse(null);
        // 👇 Добавляем путь к папке из конфигурации
        String pathToImage = appProperties.getImagePath();
        if (object == null) {
            logger.warn("❌ Объект с engName = [{}] не найден в БД!", originalName);
            labelBox.setNameRus(item.getAlterNameRus());
            labelBox.setImagePath(pathToImage + "/" + item.getAlterImageName());
        } else {
            logger.info("✅ Найден объект по engName: {}", originalName);
            labelBox.setKeyName(object.getKeyName());
            labelBox.setNameRus(object.getRusName());
            labelBox.setImagePath(pathToImage + "/" + object.getImagePath());

        }

        logger.debug("Результат маппинга: {}", labelBox);
        return labelBox;
    }


}
