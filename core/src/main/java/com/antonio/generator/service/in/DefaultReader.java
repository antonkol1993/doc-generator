package com.antonio.generator.service.in;

import com.antonio.generator.dto.InputDto;
import com.antonio.generator.dto.input.DefaultItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DefaultReader implements InputReader {
//    private static final log log = logFactory.getlog(DefaultReader.class);
    private final List<List<InputDto>> dataBlocks = new ArrayList<>();
    private final List<InputDto> currentBlock = new ArrayList<>();

    @Override
    public List<List<InputDto>> readExcel(String filePath) throws IOException {
        System.out.println(">>> Вызван readExcel: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + filePath);
        System.out.println(">>> Вызван readExcel: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + filePath);
        System.out.println(">>> Вызван readExcel: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + filePath);
        System.out.println(">>> Вызван readExcel: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + filePath);
        System.out.println(">>> Вызван readExcel: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + filePath);

        log.info("Начало обработки Excel-файла: {}", filePath);

        try (FileInputStream file = new FileInputStream((filePath));
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            log.debug("Рабочий лист '{}' успешно загружен", sheet.getSheetName());

            for (Row row : sheet) {
                log.trace("Обработка строки {}", row.getRowNum() + 1);

                if (row.getRowNum() < 2) continue;

                DefaultItem item = processDataBlock(row);
                if (item != null) {
                    currentBlock.add(item);
                    log.debug("Добавлен элемент: {}", item);
                } else if (!currentBlock.isEmpty()) {
                    log.debug("Завершен блок из {} элементов", currentBlock.size());
                    dataBlocks.add(new ArrayList<>(currentBlock));
                    currentBlock.clear();
                }
            }

            if (!currentBlock.isEmpty()) {
                dataBlocks.add(new ArrayList<>(currentBlock));
                log.debug("Добавлен последний блок из {} элементов", currentBlock.size());
            }

            log.info("Файл успешно обработан. Найдено {} блоков данных", dataBlocks.size());
            return dataBlocks;

        } catch (IOException e) {
            log.error("Ошибка при обработке Excel-файла: {}", filePath, e);
            throw e;
        }
    }

    private DefaultItem processDataBlock(Row row) {
        DefaultItem item = new DefaultItem();

        try {
            item.setItemNo(getIntegerValue(row.getCell(0)));
            item.setOriginalName(getCellValue(row.getCell(1)));
            item.setAlterNameRus(getCellValue(row.getCell(2)));
            item.setSize(getCellValue(row.getCell(3)));
            item.setMarking(getCellValue(row.getCell(4)));
            item.setQuantityInBox(getCellValue(row.getCell(5)));
            item.setOrder(getCellValue(row.getCell(6)));
            item.setAlterImagePath(getCellValue(row.getCell(7)));

            if (log.isTraceEnabled()) {
                log.trace("Прочитаны данные: {}", item);
            }

            if (isEmptyItem(item)) {
                log.debug("Пустой элемент в строке {}", row.getRowNum() + 1);
                return null;
            }

            return item;
        } catch (Exception e) {
            log.warn("Ошибка обработки строки {}: {}", row.getRowNum() + 1, e.getMessage());
            return null;
        }
    }


    private boolean isEmptyItem(DefaultItem item) {
        // Проверяем, что все поля пустые
        return (item.getItemNo() == null) &&
                (item.getOriginalName() == null || item.getOriginalName().trim().isEmpty()) &&
                (item.getAlterNameRus() == null || item.getAlterNameRus().trim().isEmpty()) &&
                (item.getSize() == null || item.getSize().trim().isEmpty()) &&
                (item.getQuantityInBox() == null || item.getQuantityInBox().trim().isEmpty()) &&
                (item.getMarking() == null || item.getMarking().trim().isEmpty()) &&
                (item.getAlterImagePath() == null || item.getAlterImagePath().trim().isEmpty()) &&
                (item.getOrder() == null || item.getOrder().trim().isEmpty());
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((int) cell.getNumericCellValue()); // Преобразуем в int, если число
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getCellFormula();
                } catch (Exception e) {
                    log.warn("Ошибка при чтении формулы в {}: {}", cell.getAddress(), e.getMessage());
                    return "ERROR_FORMULA";
                }
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    private Integer getIntegerValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            double numericValue = cell.getNumericCellValue();
            if (numericValue % 1 != 0) {
                log.warn("Число {} в ячейке {} содержит дробную часть, округление!", numericValue, cell.getAddress());
            }
            return (int) Math.round(numericValue); // Безопасное округление
        }
        if (cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

}
