package com.company;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Math.pow;

public class GaussianBlur {

    private int radius;
    private double variance;
    private double[][] weights;
    private BufferedImage sourceImage;
    private BufferedImage resultImage;
    private int xBegin;
    private int xEnd;
    private int yBegin;
    private int yEnd;


    public BufferedImage getResultImage() {
        return resultImage;
    }


    public GaussianBlur(int radius, double variance) {
        this.radius = radius;
        this.variance = variance;
        this.weights = generateWeightMatrix(radius, variance);
    }

    public void setPropertiesOfResultImage() {
        resultImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    public BufferedImage getSourceImage() {
        return sourceImage;
    }

    public void setWidthBounds(int xBegin, int xEnd) {
        this.xBegin = xBegin;
        this.xEnd = xEnd;
    }

    public void setHeightBounds(int yBegin, int yEnd) {
        this.yBegin = yBegin;
        this.yEnd = yEnd;
    }

    public void setSourceImage(BufferedImage image) {
        sourceImage = image;
        setPropertiesOfResultImage();
    }

    public void setSourceImage(String imageNameWithExtension, String pathToFolderWithImage) {
        try {
            this.sourceImage = ImageIO.read(new File(pathToFolderWithImage + "/" + imageNameWithExtension));
            setPropertiesOfResultImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int showProgress(int x, int y, int sizeOfLine, double totalNumberOfIterations, int max) {
        int currentIteration = x * sizeOfLine + y;
        double part = currentIteration / totalNumberOfIterations;
        int percent = (int)(part * 100);
        int progress = percent / 10;

        if (progress > max) {
            max = progress;
            System.out.println("progress --> " + max + "0%");
        }
        return max;
    }

    public void createGaussianedImage(int xBegin, int xEnd, int yBegin, int yEnd) {
        //answer = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        /*
        double iterations = sourceImage.getHeight() * sourceImage.getWidth();
        int max = 0;*/

        for (int x = xBegin; x <= xEnd; x++) {
            for (int y = yBegin; y <= yEnd; y++) {

                //max = showProgress(x, y, sourceImage.getHeight(), iterations, max);
                double[][] distributedColorRed = new double[radius][radius];
                double[][] distributedColorGreen = new double[radius][radius];
                double[][] distributedColorBlue = new double[radius][radius];

                for (int weightX = 0; weightX < radius/*weights.length*/; weightX++) {
                    for (int weightY = 0; weightY < radius/*weights[weightX].length*/; weightY++) {

                        int sampleX = x + weightX - (/*weights.length*/ radius / 2);
                        int sampleY = y + weightY - (/*weights.length*/ radius / 2);

                        if (sampleX > sourceImage.getWidth() - 1) {
                            int offset = sampleX - (sourceImage.getWidth() - 1);
                            sampleX = sourceImage.getWidth() - 1 - offset;
                        }

                        if (sampleY > sourceImage.getHeight() - 1) {
                            int offset = sampleY - (sourceImage.getHeight() - 1);
                            sampleY = sourceImage.getHeight() - 1 - offset;
                        }

                        if (sampleX < 0) {
                            sampleX = Math.abs(sampleX);
                        }

                        if (sampleY < 0) {
                            sampleY = Math.abs(sampleY);
                        }

                        double currentWeight = weights[weightX][weightY];

                        Color sampleColor = new Color(sourceImage.getRGB(sampleX, sampleY));

                        distributedColorRed[weightX][weightY] = currentWeight * sampleColor.getRed();
                        distributedColorGreen[weightX][weightY] = currentWeight * sampleColor.getGreen();
                        distributedColorBlue[weightX][weightY] = currentWeight * sampleColor.getBlue();

                    }
                }
                resultImage.setRGB(x, y, new Color(
                        getWeightedColorValue(distributedColorRed),
                        getWeightedColorValue(distributedColorGreen),
                        getWeightedColorValue(distributedColorBlue)).getRGB());
            }
        }
    }

    private int getWeightedColorValue(double[][] weightedColor) {
        double summation = 0;
        for (int i = 0; i < weightedColor.length; i++) {
            for (int j = 0; j < weightedColor[i].length; j++) {
                summation += weightedColor[i][j];
            }
        }
        return (int)summation;
    }

    public static void printWeightedMatrixToFile(double[][] weightMatrix) {
        BufferedImage img = new BufferedImage(weightMatrix.length, weightMatrix.length, BufferedImage.TYPE_INT_RGB);

        double max = 0;
        for (int i = 0; i < weightMatrix.length; i++) {
            for (int j = 0; j < weightMatrix[i].length; j++) {
                max = Math.max(max, weightMatrix[i][j]);
            }
        }
        for (int i = 0; i < weightMatrix.length; i++) {
            for (int j = 0; j < weightMatrix[i].length; j++) {
                int grayScale = (int)((weightMatrix[i][j] / max) * 255d);
                img.setRGB(i, j, new Color(grayScale, grayScale, grayScale).getRGB());
            }
        }
        try {
            ImageIO.write(img, "PNG", new File("gaussian-weights-graphed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //почему х и у double, а не int?
    public double gaussiamModel(int x, int y, double variance) {
        return (1 / (2 * Math.PI * pow(variance, 2))) * pow(Math.E, -(pow(x, 2) + pow(y, 2)) / (2 * pow(variance, 2)));
    }

    public double[][] generateWeightMatrix(int radius, double variance) {
        double[][] weights = new double[radius][radius];
        double summation = 0;
        //calculating weights matrix
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = gaussiamModel(i - radius / 2, j - radius / 2, variance);
                summation += weights[i][j];
            }
        }
        //performing matrix normalization (sum of all elements == 1)
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = weights[i][j] / summation;
            }
        }
        return weights;
    }
}
