package com.antonio.core.generator.for_test_jpeg;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class LabelGenerator {

    public static void generateLabel(LabelData data, String outputPath) throws Exception {
        int width = 886;
        int height = 1771;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Антиалиасинг
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        // Fonts
        Font headerFont = new Font("Arial", Font.BOLD, 60);
        Font mediumFont = new Font("Arial", Font.PLAIN, 42);
        Font smallFont = new Font("Arial", Font.PLAIN, 32);
        Font ultraSmall = new Font("Arial", Font.PLAIN, 26);

        int y = 60;
        int margin = 30;

        // PZ
        g.setFont(smallFont);
        g.setColor(Color.BLACK);
        g.drawString("PZ 2", margin, y + 30);

        // Логотип MFIX (в 2 раза увеличен и справа)
        BufferedImage logo = ImageIO.read(new File("resources/images/Mfix.jpg"));
        Image resizedLogo = logo.getScaledInstance(240, 100, Image.SCALE_SMOOTH);
        g.drawImage(resizedLogo, width - margin - 240, y - 10, null);

        y += 110;

        // Верхняя зелёная плашка
        int bannerHeight = 60;
        g.setColor(new Color(141, 198, 63));
        g.fillRect(0, y, width, bannerHeight);

        // Диагональная белая полоса (разрыв)
        int stripeWidth = -40;
        int stripeX = width / 2;
        int topY = y;
        int bottomY = y + bannerHeight;

        Polygon whiteStripe = new Polygon();
        whiteStripe.addPoint(stripeX - stripeWidth - 40, topY);
        whiteStripe.addPoint(stripeX - stripeWidth + 60, topY);
        whiteStripe.addPoint(stripeX + 60, bottomY);
        whiteStripe.addPoint(stripeX - 40, bottomY);

        g.setColor(Color.WHITE);
        g.fillPolygon(whiteStripe);

        // Текст на зелёной плашке
        g.setColor(Color.BLACK);
        g.setFont(headerFont);
        g.drawString(data.getSize(), margin, y + 50);
        g.drawString(data.getCount() + " шт.", width - 280, y + 50);

        y += bannerHeight + 30;

        // Название
        g.setFont(mediumFont);
        g.drawString(data.getDescription(), margin, y);
        y += 80;

        // Фото самореза
        BufferedImage screw = ImageIO.read(new File("resources/images/metalware/screw_for_parquet.png"));
        g.drawImage(screw, 60, y, width - 120, 100, null);
        y += 120;

        // Таблица характеристик
        g.setFont(smallFont);
        g.setColor(new Color(102, 204, 0));
        g.drawString("МАТЕРИАЛ :", margin, y);
        g.drawString("ПОКРЫТИЕ :", margin, y + 40);
        g.drawString("НАЗНАЧЕНИЕ :", margin, y + 80);

        g.setColor(Color.BLACK);
        g.drawString(data.getMaterial(), 250, y);
        g.drawString(data.getCoating(), 250, y + 40);
        g.drawString(data.getPurpose(), 250, y + 80);
        y += 130;

        // Тех. чертеж
        BufferedImage tech = ImageIO.read(new File("resources/images/metalware/screw_for_parquet.png"));
        g.drawImage(tech, 60, y, width - 120, 120, null);
        y += 140;

        // Диаметр и длина
        g.setFont(smallFont);
        g.drawString(data.getDiameter(), 60, y);
        g.drawString(data.getLength(), 60, y + 40);
        y += 100;

        // Название ещё раз
        g.setFont(mediumFont);
        g.drawString(data.getDescription(), margin, y);
        y += 70;

        // Повтор размера и количества на зелёном фоне
        g.setColor(new Color(179, 225, 78));
        g.fillRect(0, y, width, 100);
        g.setColor(Color.BLACK);
        g.setFont(headerFont);
        g.drawString(data.getSize(), margin, y + 70);
        g.drawString(data.getCount() + " шт.", width - 280, y + 70);
        y += 130;

        // Производитель, импортер, срок
        g.setFont(ultraSmall);
        g.setColor(Color.BLACK);
        g.drawString("ИЗГОТОВИТЕЛЬ: " + data.getManufacturer(), margin, y);
        g.drawString("ИМПОРТЕР: " + data.getImporter(), margin, y + 35);
        g.drawString("Срок годности не ограничен.", margin, y + 70);
        g.drawString("Дата изготовления: " + data.getProductionDate(), margin, y + 105);
        y += 160;

        // Штрихкод
        BufferedImage barcode = ImageIO.read(new File("resources/images/metalware/screw_for_parquet.png"));
        g.drawImage(barcode, width / 2 - 200, y, 400, 60, null);

        g.dispose();
        ImageIO.write(image, "png", new File(outputPath));
    }
}
