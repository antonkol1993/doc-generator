package com.antonio.core.generator.service.out;

import com.antonio.config.component.AppProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Сервис для вставки изображений и логотипов в Excel-файлы с центрированием и масштабированием.
 */
@Service
@RequiredArgsConstructor
public class ImageHandlerToExcel {

    private final AppProperties appProperties;
    private static final Logger log = LoggerFactory.getLogger(ImageHandlerToExcel.class);

    /**
     * Добавляет изображение (включая логотип) на лист Excel, с учетом центрирования и масштабирования.
     *
     * @param workbook  рабочая книга
     * @param sheet     лист Excel
     * @param imageName имя файла изображения (например, "image.png" или "logo.png")
     * @param startRow  начальная строка (0-based)
     * @param startCol  начальный столбец (0-based)
     * @param endRow    конечная строка (0-based)
     * @param endCol    конечный столбец (0-based)
     * @param isLogo    true, если вставляется логотип (используется другой путь)
     * @return true — если изображение успешно вставлено, иначе false
     */
    public boolean addImageToSheet(Workbook workbook, Sheet sheet, String imageName,
                                   int startRow, int startCol, int endRow, int endCol,
                                   boolean isLogo) {
        try {
            String basePath = isLogo
                    ? appProperties.getLogo().getPath()
                    : appProperties.getImage().getPath();

            Path imagePath = Path.of(basePath, imageName);

            if (!Files.exists(imagePath)) {
                log.warn("⚠️ Изображение не найдено по пути: {}", imagePath.toAbsolutePath());
                return false;
            }

            byte[] imageBytes;
            try (InputStream inputStream = Files.newInputStream(imagePath)) {
                imageBytes = IOUtils.toByteArray(inputStream);
            }

            int pictureType;
            String lowerCaseName = imageName.toLowerCase();
            if (lowerCaseName.endsWith(".png")) {
                pictureType = Workbook.PICTURE_TYPE_PNG;
            } else if (lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".jpeg")) {
                pictureType = Workbook.PICTURE_TYPE_JPEG;
            } else {
                log.warn("⚠️ Неподдерживаемый формат изображения: {}", imageName);
                return false;
            }

            int pictureIdx = workbook.addPicture(imageBytes, pictureType);

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            if (bufferedImage == null) {
                log.warn("⚠️ Не удалось прочитать изображение: {}", imageName);
                return false;
            }

            int originalWidth = bufferedImage.getWidth();
            int originalHeight = bufferedImage.getHeight();

            float cellWidthPx = 0;
            for (int col = startCol; col <= endCol; col++) {
                cellWidthPx += sheet.getColumnWidthInPixels(col);
            }

            float cellHeightPx = 0;
            for (int row = startRow; row <= endRow; row++) {
                Row sheetRow = sheet.getRow(row);
                if (sheetRow != null) {
                    cellHeightPx += sheetRow.getHeightInPoints() * 1.33f;
                }
            }

            double scale = 1.0;
            if (originalWidth > cellWidthPx || originalHeight > cellHeightPx) {
                double scaleX = (cellWidthPx * 0.8) / originalWidth;
                double scaleY = (cellHeightPx * 0.8) / originalHeight;
                scale = Math.min(scaleX, scaleY);
            }

            int newWidth = (int) (originalWidth * scale);
            int newHeight = (int) (originalHeight * scale);

            double offsetX = (cellWidthPx - newWidth) / 2.0;
            double offsetY = (cellHeightPx - newHeight) / 2.0;

            int dx1 = (int) (offsetX * 9525);
            int dy1 = (int) (offsetY * 9525);

            if (workbook instanceof XSSFWorkbook) {
                XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                XSSFClientAnchor anchor = new XSSFClientAnchor(
                        dx1, dy1, -dx1, -dy1,
                        startCol, startRow,
                        endCol + 1, endRow + 1
                );
                drawing.createPicture(anchor, pictureIdx);
            } else {
                log.warn("⚠️ Центрирование поддерживается только в XSSFWorkbook.");
                return false;
            }

            return true;

        } catch (Exception e) {
            log.error("❌ Не удалось вставить изображение в Excel: {}", imageName, e);
            return false;
        }
    }

}
