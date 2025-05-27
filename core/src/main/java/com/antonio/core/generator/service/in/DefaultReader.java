package com.antonio.core.generator.service.in;

import com.antonio.core.generator.dto.InputDto;
import com.antonio.core.generator.dto.input.DefaultItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.antonio.core.util.AnsiColors.*;

@Service("defaultReader")
@Slf4j
public class DefaultReader implements InputReader {


    @Override
    public List<List<InputDto>> readExcel(String filePath) throws IOException {
        List<List<InputDto>> dataBlocks = new ArrayList<>();
        List<InputDto> currentBlock = new ArrayList<>();
        log.info("📂 Начало обработки Excel-файла: {}", filePath);

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            log.debug("📄 Рабочий лист '{}' успешно загружен", sheet.getSheetName());

            for (Row row : sheet) {
                int rowIndex = row.getRowNum();
                log.trace("➡️ Обработка строки №{}", rowIndex + 1);

                if (rowIndex < 2) {
                    log.trace("⏭️ Пропускаем строку заголовка №{}", rowIndex + 1);
                    continue;
                }

                DefaultItem item = processDataBlock(row);
                if (item != null) {
                    currentBlock.add(item);
                    log.debug(color("✅ Добавлен элемент: " + item, YELLOW));
                } else if (!currentBlock.isEmpty()) {
                    log.debug(color("🔶 Завершен блок из " + currentBlock.size() + " элементов", YELLOW));
                    dataBlocks.add(new ArrayList<>(currentBlock));
                    currentBlock.clear();
                }
            }

            if (!currentBlock.isEmpty()) {
                dataBlocks.add(new ArrayList<>(currentBlock));
                log.debug(color("🔶 Добавлен последний блок из " + currentBlock.size() + " элементов", YELLOW));
            }

            log.info(color("🎉 Файл успешно зачитан. Найдено " + dataBlocks.size() + " блоков данных", GREEN));
            return dataBlocks;

        } catch (IOException e) {
            log.error(color("❌ Ошибка при обработке Excel-файла: " + filePath, RED), e);
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
            item.setAlterImageName(getCellValue(row.getCell(7)));

            if (log.isTraceEnabled()) {
                log.trace("Прочитаны данные: {}", item);
            }

            if (isEmptyItem(item)) {
                log.debug("⚠️ Пустой элемент в строке {}", row.getRowNum() + 1);
                return null;
            }

            return item;

        } catch (Exception e) {
            log.warn(color("❌ Ошибка обработки строки " + (row.getRowNum() + 1) + ": " + e.getMessage(), RED));
            return null;
        }
    }

    private boolean isEmptyItem(DefaultItem item) {
        return (item.getItemNo() == null) &&
                isBlank(item.getOriginalName()) &&
                isBlank(item.getAlterNameRus()) &&
                isBlank(item.getSize()) &&
                isBlank(item.getQuantityInBox()) &&
                isBlank(item.getMarking()) &&
                isBlank(item.getAlterImageName()) &&
                isBlank(item.getOrder());
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> DateUtil.isCellDateFormatted(cell)
                    ? cell.getDateCellValue().toString()
                    : String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> {
                try {
                    yield cell.getCellFormula();
                } catch (Exception e) {
                    log.warn(color("❌ Ошибка при чтении формулы в " + cell.getAddress() + ": " + e.getMessage(), RED));
                    yield "ERROR_FORMULA";
                }
            }
            case BLANK -> "";
            default -> ""; // на случай _NONE или чего-то нестандартного
        };
    }

    private Integer getIntegerValue(Cell cell) {
        if (cell == null) return null;

        if (cell.getCellType() == CellType.NUMERIC) {
            double value = cell.getNumericCellValue();
            if (value % 1 != 0) {
                log.warn(color("⚠️ Число " + value + " в ячейке " + cell.getAddress() + " содержит дробную часть, округление!", RED));
            }
            return (int) Math.round(value);
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
