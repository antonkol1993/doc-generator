package com.antonio.core.generator.service.out;

import com.antonio.config.component.AppProperties;
import com.antonio.config.component.CommonPropertiesLargeBox;
import com.antonio.core.generator.dto.output.LabelLargeBox;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutputWriterXLSXImpl implements OutputWriter<LabelLargeBox> {

    private final ImageHandlerToExcel imageHandlerToExcel;
    private final CommonPropertiesLargeBox commonProperties;
    private final AppProperties appProperties;

    private static final Logger log = LoggerFactory.getLogger(ImageHandlerToExcel.class);

    @Override
    public void generateCards(List<List<LabelLargeBox>> dataBlocks, String outputName) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            int startRow = 2;
            int startCol = 2;
            int tempCol = startCol;

            String logoFileName = appProperties.getLogo().getFileName();

            for (List<LabelLargeBox> block : dataBlocks) {
                for (LabelLargeBox item : block) {
                    addCard(workbook, sheet, item, logoFileName, startRow, startCol);
                    startCol += 4;
                }
                startRow += 12;
                startCol = tempCol;
            }

            try (FileOutputStream fileOut = new FileOutputStream(outputName)) {
                workbook.write(fileOut);
            } catch (IOException e) {
                System.out.println("не получилось записать файл");
            }
        }
    }

    private void addCard(Workbook workbook, Sheet sheet, LabelLargeBox item, String logoFileName,
                         int startRow, int startCol) throws IOException {
        String markingLabel = commonProperties.get("marking");
        String sizeLabel = commonProperties.get("size_label");
        String quantityLabel = commonProperties.get("quantity");
        String pcsLabel = commonProperties.get("pcs");
        String weightLabel = commonProperties.get("weight");
        String kgLabel = commonProperties.get("kg");
        String madeInLabel = commonProperties.get("made_in");
        String orderLabel = commonProperties.get("order_label");

        CellStyle style1 = createCellStyle(workbook, "Arial", false, BorderStyle.MEDIUM, HorizontalAlignment.CENTER, (short) 10);
        CellStyle style2 = createCellStyle(workbook, "Arial", true, BorderStyle.MEDIUM, HorizontalAlignment.CENTER, (short) 11);
        CellStyle style3 = createCellStyle(workbook, "Arial", true, BorderStyle.THIN, HorizontalAlignment.CENTER, (short) 10);
        CellStyle style4 = createCellStyle(workbook, "Arial", true, BorderStyle.THIN, HorizontalAlignment.GENERAL, (short) 10);

        setColumnWidths(sheet, startCol);
        setRowHeights(sheet, startRow);

        createMergedCell(workbook, sheet, startRow, startCol + 1, startRow, startCol + 3, "", style1);
        createMergedCell(workbook, sheet, startRow + 1, startCol + 1, startRow + 1, startCol + 3, "", style2);
        createMergedCell(workbook, sheet, startRow + 2, startCol + 1, startRow + 2, startCol + 3,
                item.getNameRus() + "\n" + item.getSize(), style2);

        createCell(sheet, startRow + 3, startCol + 1, markingLabel, style4);
        createMergedCell(workbook, sheet, startRow + 3, startCol + 2, startRow + 3, startCol + 3, item.getMarking(), style3);

        createCell(sheet, startRow + 4, startCol + 1, sizeLabel, style4);
        createMergedCell(workbook, sheet, startRow + 4, startCol + 2, startRow + 4, startCol + 3, item.getSize(), style3);

        createCell(sheet, startRow + 5, startCol + 1, "", style4);
        createMergedCell(workbook, sheet, startRow + 5, startCol + 2, startRow + 5, startCol + 3, "", style3);

        createCell(sheet, startRow + 6, startCol + 1, quantityLabel, style4);
        createCell(sheet, startRow + 6, startCol + 2, item.getQuantityInBox(), style3);
        createCell(sheet, startRow + 6, startCol + 3, pcsLabel, style4);

        createCell(sheet, startRow + 7, startCol + 1, weightLabel, style4);
        createCell(sheet, startRow + 7, startCol + 2, "", style3);
        createCell(sheet, startRow + 7, startCol + 3, kgLabel, style4);

        createCell(sheet, startRow + 8, startCol + 1, "", style4);
        createMergedCell(workbook, sheet, startRow + 8, startCol + 2, startRow + 8, startCol + 3, madeInLabel, style4);

        createCell(sheet, startRow + 9, startCol + 1, orderLabel, style4);
        createMergedCell(workbook, sheet, startRow + 9, startCol + 2, startRow + 9, startCol + 3, item.getOrder(), style4);

        for (int i = startRow + 2; i <= startRow + 9; i++) {
            autoSizeRow(sheet, i);
        }

        tryAddImageIfExists(workbook, sheet, appProperties.getLogo().getFileName(), true,
                startRow - 1, startCol, startRow - 1, startCol + 2, String.valueOf(item.getItemNo()));

        tryAddImageIfExists(workbook, sheet, item.getImageName(), false,
                startRow, startCol, startRow, startCol + 2, String.valueOf(item.getItemNo()));
    }

    private void tryAddImageIfExists(Workbook workbook, Sheet sheet, String imageName, boolean isLogo,
                                     int row1, int col1, int row2, int col2, String itemNo) {
        if (imageName == null || imageName.isBlank() || !imageName.contains(".")) {
            log.warn("⚠️ {} не задан или не содержит расширения: {}", isLogo ? "Логотип" : "Картинка", itemNo);
            return;
        }

        String basePath = isLogo ? appProperties.getLogo().getPath() : appProperties.getImage().getPath();
        Path imagePath = Path.of(basePath, imageName);

        if (!Files.exists(imagePath) || Files.isDirectory(imagePath)) {
            log.warn("⚠️ {} отсутствует или это директория: {} → {}", isLogo ? "Логотип" : "Картинка", itemNo, imagePath.toAbsolutePath());
            return;
        }

        try {
            boolean added = imageHandlerToExcel.addImageToSheet(workbook, sheet, imageName, row1, col1, row2, col2, isLogo);
            if (added) {
                log.info("✅ {} добавлен: {}", isLogo ? "Логотип" : "Картинка", itemNo);
            } else {
                log.warn("⚠️ addImageToSheet вернул false для {}: {}", isLogo ? "логотипа" : "картинки", itemNo);
            }
        } catch (Exception e) {
            log.error("❌ Ошибка при вставке {} для {}: {}", isLogo ? "логотипа" : "картинки", itemNo, e.toString());
        }
    }

    private CellStyle createCellStyle(Workbook workbook, String fontName, boolean bold, BorderStyle border,
                                      HorizontalAlignment alignment, short fontSize) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setBold(bold);
        font.setFontHeightInPoints(fontSize);
        style.setFont(font);
        style.setAlignment(alignment);
        style.setBorderBottom(border);
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setWrapText(true);
        return style;
    }

    private void setColumnWidths(Sheet sheet, int startCol) {
        sheet.setColumnWidth(startCol, (int) (((124 - 5) / 7.0 + 0.71) * 256));
        sheet.setColumnWidth(startCol + 1, (int) (((88 - 5) / 7.0 + 0.71) * 256));
        sheet.setColumnWidth(startCol + 2, (int) (((88 - 5) / 7.0 + 0.71) * 256));
    }

    private void setRowHeights(Sheet sheet, int startRow) {
        setRowHeight(sheet, startRow, 73.5f);
        setRowHeight(sheet, startRow + 1, 69.0f);
        setRowHeight(sheet, startRow + 2, 35.25f);
    }

    private void setRowHeight(Sheet sheet, int rowIndex, float height) {
        Row row = sheet.getRow(rowIndex - 1);
        if (row == null) {
            row = sheet.createRow(rowIndex - 1);
        }
        row.setHeightInPoints(height);
    }

    private void createCell(Sheet sheet, int row, int col, String value, CellStyle style) {
        Row sheetRow = sheet.getRow(row - 1);
        if (sheetRow == null) {
            sheetRow = sheet.createRow(row - 1);
        }
        Cell cell = sheetRow.createCell(col - 1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void createMergedCell(Workbook workbook, Sheet sheet, int startRow, int startCol, int endRow, int endCol,
                                  String value, CellStyle style) {
        sheet.addMergedRegion(new CellRangeAddress(startRow - 1, endRow - 1, startCol - 1, endCol - 1));
        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                createCell(sheet, row, col, "", style);
            }
        }
        createCell(sheet, startRow, startCol, value, style);
    }

    private void autoSizeRow(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex - 1);
        if (row != null) {
            int maxTextLength = 0;
            for (Cell cell : row) {
                if (cell.getCellType() == CellType.STRING) {
                    maxTextLength = Math.max(maxTextLength, cell.getStringCellValue().length());
                }
            }
            int lineCount = (int) Math.ceil(maxTextLength / 20.0);
            row.setHeightInPoints(lineCount * sheet.getDefaultRowHeightInPoints());
        }
    }
}
