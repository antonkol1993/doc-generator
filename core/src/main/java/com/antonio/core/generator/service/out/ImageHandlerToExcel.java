package com.antonio.core.generator.service.out;

import com.antonio.config.component.AppProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
@RequiredArgsConstructor
public class ImageHandlerToExcel {

    private final AppProperties appProperties;



    public boolean addImageToSheet(Workbook workbook, Sheet sheet, String relativeImagePath,
                                   int startRow, int startCol, int endRow, int endCol) throws IOException {

        File currentDir = new File(System.getProperty("user.dir")); // останемся в doc-generator
        File imageFile = new File(currentDir, relativeImagePath);   // путь к картинке

        if (!imageFile.exists()) {
            System.err.println("❌ Картинка не найдена: " + imageFile.getAbsolutePath());
            return false;
        }

        byte[] imageBytes;
        try (InputStream inputStream = new FileInputStream(imageFile)) {
            imageBytes = IOUtils.toByteArray(inputStream);
        }

        // Определяем тип изображения
        int pictureType;
        String lowerCasePath = relativeImagePath.toLowerCase();
        if (lowerCasePath.endsWith(".png")) {
            pictureType = Workbook.PICTURE_TYPE_PNG;
        } else if (lowerCasePath.endsWith(".jpg") || lowerCasePath.endsWith(".jpeg")) {
            pictureType = Workbook.PICTURE_TYPE_JPEG;
        } else {
            throw new IllegalArgumentException("Поддерживаются только PNG и JPEG изображения.");
        }

        int pictureIdx = workbook.addPicture(imageBytes, pictureType);

        // Получаем размеры изображения
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        int originalWidth = bufferedImage.getWidth();
        int originalHeight = bufferedImage.getHeight();

        // Размеры области размещения (в пикселях)
        float cellWidthPx = 0;
        for (int col = startCol; col <= endCol; col++) {
            cellWidthPx += sheet.getColumnWidthInPixels(col);
        }

        float cellHeightPx = 0;
        for (int row = startRow; row <= endRow; row++) {
            Row sheetRow = sheet.getRow(row);
            if (sheetRow != null) {
                cellHeightPx += sheetRow.getHeightInPoints() * 1.33f; // Преобразуем в пиксели
            }
        }

        // Масштаб
        double scale = 1.0;
        if (originalWidth > cellWidthPx || originalHeight > cellHeightPx) {
            double scaleX = (cellWidthPx * 0.8) / originalWidth;
            double scaleY = (cellHeightPx * 0.8) / originalHeight;
            scale = Math.min(scaleX, scaleY);
        }

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        // Центрирование
        double offsetX = (cellWidthPx - newWidth) / 2.0;
        double offsetY = (cellHeightPx - newHeight) / 2.0;

        int dx1 = (int) (offsetX * 9525);
        int dy1 = (int) (offsetY * 9525);

        // Вставка
        if (workbook instanceof XSSFWorkbook) {
            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(dx1, dy1, -dx1, -dy1, startCol, startRow, endCol + 1, endRow + 1);
            drawing.createPicture(anchor, pictureIdx);
        }

        return true;
    }
}
