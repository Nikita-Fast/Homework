package com.company;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import static java.lang.Math.pow;

public class GaussianBlur {
    private static final String pathToFolderWithInput = Paths.get("resources/").toAbsolutePath() + "/";

    public static BufferedImage blur(BufferedImage inputImage, int threadsNumber, boolean isVertical,
                                     int radius, double deviation) throws InterruptedException {
        BufferedImage resultImage = new BufferedImage(inputImage.getWidth(),
                inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        double[][] weights = generateWeightMatrixStatic(radius, deviation);

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new BlurRunnable(i, threadsNumber, radius, weights,
                    inputImage, resultImage, isVertical));
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return resultImage;
    }

    public static void applyWeightMatrixToPixel(int y, int x, int radius, double[][] matrix,
                                                 BufferedImage inputImage, BufferedImage resultImage) {
        int offset = radius / 2;
        int maxHeight = inputImage.getHeight();
        int maxWidth = inputImage.getWidth();
        double overallRed = 0.0;
        double overallGreen = 0.0;
        double overallBlue = 0.0;

        for (int i = 0; i < radius; i++) {
            for (int j = 0; j < radius; j++) {
                int currY = y - offset + i;
                int currX = x - offset + j;
                if (0 <= currY && currY < maxHeight && 0 <= currX && currX < maxWidth) {
                    int rgb = inputImage.getRGB(currX, currY);
                    Color color = new Color(rgb);
                    double factor =  matrix[i][j];
                    overallRed += color.getRed() * factor;
                    overallGreen += color.getGreen() * factor;
                    overallBlue += color.getBlue() * factor;
                }
            }
        }
        resultImage.setRGB(x, y, new Color((int)overallRed, (int)overallGreen, (int)overallBlue).getRGB());
    }

    public static void saveResultImage(BufferedImage resultImage, String name) {
        try {
            ImageIO.write(resultImage, "PNG", new File(name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage readImage(String name) {
        BufferedImage image = null;
        try {
            File file = new File(pathToFolderWithInput + name);
            image = ImageIO.read(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (image == null) {
            throw new RuntimeException("failed to read image!");
        }
        return image;
    }


    public static double[][] generateWeightMatrixStatic(int radius, double deviation) {
        double[][] weights = new double[radius][radius];
        double sum = 0;
        //calculating weights matrix
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                double weight = gaussianFunction(i - radius / 2, j - radius / 2, deviation);
                weights[i][j] = weight;
                sum += weight;
            }
        }
        //performing matrix normalization (sum of all elements == 1)
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = weights[i][j] / sum;
            }
        }
        return weights;
    }

    public static double gaussianFunction(int x, int y, double deviation) {
        return (1 / (2 * Math.PI * pow(deviation, 2))) * pow(Math.E, -(pow(x, 2) + pow(y, 2)) / (2 * pow(deviation, 2)));
    }
}
