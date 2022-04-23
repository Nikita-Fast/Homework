package com.company;

import java.awt.image.BufferedImage;

public class BlurRunnable implements Runnable{
    private final int number;
    private final int step;
    private final int radius;
    private final double[][] matrix;
    private final BufferedImage inputImage;
    private final BufferedImage resultImage;
    private final boolean isVertical;

    public BlurRunnable(int number, int step, int radius, double[][] matrix, BufferedImage inputImage,
                        BufferedImage resultImage, boolean isVertical) {
        this.number = number;
        this.step = step;
        this.radius = radius;
        this.matrix = matrix;
        this.inputImage = inputImage;
        this.resultImage = resultImage;
        this.isVertical = isVertical;
    }

    @Override
    public void run() {
        if (!isVertical) { //полосы горизонтальные
            for (int y = number; y < inputImage.getHeight(); y += step) {
                for (int x = 0; x < inputImage.getWidth(); x++) {
                    GaussianBlur.applyWeightMatrixToPixel(y, x, radius, matrix, inputImage, resultImage);
                }
            }
        }
        else { //полосы вертикальные
            for (int x = number; x < inputImage.getWidth(); x += step) {
                for (int y = 0; y < inputImage.getHeight(); y++) {
                    GaussianBlur.applyWeightMatrixToPixel(y, x, radius, matrix, inputImage, resultImage);
                }
            }
        }
    }
}
