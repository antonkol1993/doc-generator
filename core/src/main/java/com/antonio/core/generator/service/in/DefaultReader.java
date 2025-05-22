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

@Service("defaultReader")
@Slf4j
public class DefaultReader implements InputReader {
    //    private static final log log = logFactory.getlog(DefaultReader.class);
    private final List<List<InputDto>> dataBlocks = new ArrayList<>();
    private final List<InputDto> currentBlock = new ArrayList<>();

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GREEN = "\u001B[32m";

    @Override
    public List<List<InputDto>> readExcel(String filePath) throws IOException {
        log.info("📂 Начало обработки Excel-файла: {}", filePath);

        try (FileInputStream file = new FileInputStream((filePath));
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            log.debug("📄 Рабочий лист '{}' успешно загружен", sheet.getSheetName());

            for (Row row : sheet) {
                log.trace("➡️ Обработка строки №{}", row.getRowNum() + 1);

                if (row.getRowNum() < 2) {
                    log.trace("⏭️ Пропускаем заголовок/первую строку {}", row.getRowNum() + 1);
                    continue;
                }

                DefaultItem item = processDataBlock(row);
                if (item != null) {
                    currentBlock.add(item);
                    log.debug(ANSI_YELLOW + "✅ Добавлен элемент: {}" + ANSI_RESET, item);
                } else if (!currentBlock.isEmpty()) {
                    log.debug(ANSI_YELLOW + "🔶 Завершен блок из {} элементов" + ANSI_RESET, currentBlock.size());
                    dataBlocks.add(new ArrayList<>(currentBlock));
                    currentBlock.clear();
                }
            }

            if (!currentBlock.isEmpty()) {
                dataBlocks.add(new ArrayList<>(currentBlock));
                log.debug(ANSI_YELLOW + "🔶 Добавлен последний блок из {} элементов" + ANSI_RESET, currentBlock.size());
            }

            log.info(ANSI_GREEN + "🎉 Файл успешно зачитан. Найдено {} блоков данных" + ANSI_RESET, dataBlocks.size());
            return dataBlocks;

        } catch (IOException e) {
            log.error(ANSI_RED + "❌ Ошибка при обработке Excel-файла: {}" + ANSI_RESET, filePath, e);
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
                log.debug("Пустой элемент в строке {}", row.getRowNum() + 1);
                return null;
            }

            return item;

        } catch (Exception e) {
            log.warn(ANSI_RED + "❌ Ошибка обработки строки {}: {}" + ANSI_RESET, row.getRowNum() + 1, e.getMessage());
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
                (item.getAlterImageName() == null || item.getAlterImageName().trim().isEmpty()) &&
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
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getCellFormula();
                } catch (Exception e) {
                    log.warn(ANSI_RED + "❌ Ошибка при чтении формулы в {}: {}" + ANSI_RESET,
                            cell.getAddress(), e.getMessage());
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
                log.warn(ANSI_RED + "⚠️ Число {} в ячейке {} содержит дробную часть, округление!" +
                        ANSI_RESET, numericValue, cell.getAddress());
            }
            return (int) Math.round(numericValue);
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
