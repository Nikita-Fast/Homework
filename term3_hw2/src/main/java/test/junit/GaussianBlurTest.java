package test.junit;

import com.company.GaussianBlur;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GaussianBlurTest {

    @ParameterizedTest
    @ValueSource(ints = {1,2,4,8,16})
    void blurVertical(int threadsNumber) throws InterruptedException {
        BufferedImage input = GaussianBlur.readImage("middle.jpg");
        BufferedImage res = GaussianBlur.blur(input, threadsNumber, true, 5, 2);
        GaussianBlur.saveResultImage(res, "v-" + threadsNumber + "-threads");
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,4,8,16})
    void blurHorizontal(int threadsNumber) throws InterruptedException {
        BufferedImage input = GaussianBlur.readImage("middle.jpg");
        BufferedImage res = GaussianBlur.blur(input, threadsNumber, false, 5, 2);
        GaussianBlur.saveResultImage(res, "h-" + threadsNumber + "-threads");
    }
}