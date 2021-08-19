package test;

import com.company.GaussianBlur;
import org.openjdk.jmh.annotations.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Warmup(iterations = 3, time = 10)
@Measurement(iterations = 3, time = 10)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MyBenchmark {

    GaussianBlur gaussianBlur;

    @Param({"small", "middle", "big"})
    public String imageSize;

    @Setup(Level.Trial)
    public void prepare() {
        String basePath = new File("").getAbsolutePath();
        int radius = 5;
        double variance = 1.5;
        gaussianBlur = new GaussianBlur(radius, variance);
        gaussianBlur.setSourceImage(imageSize + ".jpg", basePath);
    }

    @Param({"16", "8", "4", "2", "1"})
    public int threadsNumber;

    @Benchmark
    public void isVertical() { //processed lines are vertical
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadsNumber; i++) {
            final int initValue = i;
            Thread thread = new Thread(() -> {
                int limit = gaussianBlur.getSourceImage().getWidth() - 1;
                int x = initValue;
                while (x <= limit) {
                    gaussianBlur.createGaussianedImage(x, x, 0, gaussianBlur.getSourceImage().getHeight() - 1);
                    x += threadsNumber;
                }
            });
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Benchmark
    public void isHorizontal() { //processed lines are horizontal
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadsNumber; i++) {
            final int initValue = i;
            Thread thread = new Thread(() -> {
                int limit = gaussianBlur.getSourceImage().getHeight() - 1;
                int y = initValue;
                while (y <= limit) {
                    gaussianBlur.createGaussianedImage(0, gaussianBlur.getSourceImage().getWidth() - 1, y, y);
                    y += threadsNumber;
                }
            });
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    @TearDown(Level.Invocation)
    public void saveAndCheckResult() {
        try {
            ImageIO.write(gaussianBlur.getResultImage(), "PNG", new File("resultJMH.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!imagesAreEqual("resultJMH.png", imageSize + "Sample.png")) {
            System.exit(1091);
        }
    }

    public boolean imagesAreEqual(String name1, String name2) {
        BufferedImage im1 = null;
        BufferedImage im2 = null;

        try {
            im1 = ImageIO.read(new File(new File("").getAbsolutePath() + "/" + name1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            im2 = ImageIO.read(new File(new File("").getAbsolutePath() + "/" + name2));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (im1.getWidth() != im2.getWidth() || im1.getHeight() != im2.getHeight()) {
            return false;
        }
        for (int x = 0; x < im1.getWidth(); x++) {
            for (int y = 0; y < im1.getHeight(); y++) {
                if (im1.getRGB(x, y) != im2.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
}
