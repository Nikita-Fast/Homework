package test.jmh;

import com.company.GaussianBlur;
import org.openjdk.jmh.annotations.*;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class PerformanceTest {

    public static final String EXTENSION = ".jpg";
    private BufferedImage input;

    @Param({"true", "false"})
    public boolean vertical;

    @Param({"small", "middle", "big"})
    public String fileName;

    @Param({"1","2", "4", "8", "16"})
    public int threadsNumber;

    @Setup(Level.Trial)
    public void setupInput() {
        input = GaussianBlur.readImage(fileName + EXTENSION);
    }

    @Benchmark
    public BufferedImage blur() throws InterruptedException {
        return GaussianBlur.blur(input, threadsNumber, vertical, 5, 1.5);
    }
}
