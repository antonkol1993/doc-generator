package com.antonio.docgenerator.trying11111111IMAGE;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


    public class SimpleCardGenerator {

        public void generateCard(String outputPath) throws IOException {
            int width = 600;
            int height = 800;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Фон
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            int margin = 30;
            int y = margin;

            // Загрузка и вставка логотипа Mfix
            BufferedImage logo = ImageIO.read(new File("C:\\Users\\User\\IdeaProjects\\doc-generator\\resources\\images\\Mfix.jpg"));
            Image resizedLogo = logo.getScaledInstance(120, 50, Image.SCALE_SMOOTH);
            g.drawImage(resizedLogo, width - margin - 120, y, null);

            // Смещаем y под логотип
            y += 70;

            // Верхняя зелёная плашка
            g.setColor(new Color(141, 198, 63));
            g.fillRect(0, y, width, 60);

// Диагональная белая полоса (обратный угол, шире в 3 раза)
            int stripeWidth = -40; // ширина "разрыва" — можно настроить
            int stripeX = width / 2; // центр примерно между двумя текстами
            int topY = y;
            int bottomY = y + 60;

            Polygon whiteStripe = new Polygon();
            whiteStripe.addPoint(stripeX - stripeWidth - 40, topY);     // верхняя левая
            whiteStripe.addPoint(stripeX - stripeWidth + 60, topY); // верхняя правая
            whiteStripe.addPoint(stripeX + 60, bottomY);            // нижняя правая
            whiteStripe.addPoint(stripeX - 40, bottomY);                 // нижняя левая

            g.setColor(Color.WHITE);
            g.fillPolygon(whiteStripe);


// Текст на плашке
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.drawString("ПМ 3,5 X 11", margin, y + 40);
            g.drawString("1000 шт.", width - 180, y + 40);


            y += 80;

            // Подзаголовок
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Саморез ПМ 3,5 x 9,5 острый , фосфатированный", margin, y);
            y += 40;

            // МАТЕРИАЛ / ПОКРЫТИЕ / НАЗНАЧЕНИЕ
            Font labelFont = new Font("Arial", Font.BOLD, 16);
            Font valueFont = new Font("Arial", Font.PLAIN, 16);

            g.setFont(labelFont);
            g.setColor(new Color(141, 198, 63));
            g.drawString("МАТЕРИАЛ :", margin, y);
            g.setColor(Color.BLACK);
            g.setFont(valueFont);
            g.drawString("Сталь", 180, y);
            y += 30;

            g.setFont(labelFont);
            g.setColor(new Color(141, 198, 63));
            g.drawString("ПОКРЫТИЕ :", margin, y);
            g.setColor(Color.BLACK);
            g.setFont(valueFont);
            g.drawString("Цинк", 180, y);
            y += 30;

            g.setFont(labelFont);
            g.setColor(new Color(141, 198, 63));
            g.drawString("НАЗНАЧЕНИЕ :", margin, y);
            g.setColor(Color.BLACK);
            g.setFont(valueFont);
            g.drawString("Для крепления металлических профилей", 180, y);
            y += 60;

            // Нижняя зелёная плашка
            g.setColor(new Color(141, 198, 63));
            g.fillRect(0, y, width, 40);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 22));
            g.drawString("ПМ 3,5 X 11", margin, y + 28);
            g.drawString("1000 шт.", width - 180, y + 28);
            y += 60;

            // Производитель и дата
            g.setFont(new Font("Arial", Font.PLAIN, 14));
            g.setColor(Color.BLACK);
            g.drawString("ИЗГОТОВИТЕЛЬ: YUYAO MEIGESI FASTENER CO.,LTD", margin, y); y += 20;
            g.drawString("Импортер: ООО \"М Групп Торг\" РБ, Минск", margin, y); y += 20;
            g.drawString("Срок годности не ограничен.", margin, y); y += 20;
            g.drawString("Дата изготовления: 2025 г.", margin, y); y += 40;

            // Штрихкод текстом
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("4814187016367", width / 2 - 70, y);

            g.dispose();

            ImageIO.write(image, "png", new File(outputPath));
            System.out.println("Карточка сохранена в " + outputPath);
        }

    }



