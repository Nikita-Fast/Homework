package battleshipGame;

import Players.EasyBot;
import Players.HardBot;
import Players.Human;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {
    @Bean
    @Scope("prototype")
    public Cell cell() {
        return new Cell(0, 0);
    }

    @Bean
    @Scope("prototype")
    public Ship ship() {
        return new Ship(0, false);
    }

    @Bean
    @Scope("prototype")
    public Point2D point2D() {
        return new Point2D(0, 0);
    }

    @Bean
    @Scope("prototype")
    public DataForShot dataForShot() {
        return new DataForShot();
    }

    @Bean
    @Scope("prototype")
    public Human human() {
        return new Human();
    }

    @Bean
    @Scope("prototype")
    public HardBot hardBot() {
        return new HardBot();
    }

    @Bean
    @Scope("prototype")
    public EasyBot easyBot() {
        return new EasyBot();
    }

    @Bean
    @Scope("singleton")
    public Scene scene() {
        return new Scene(pane());
    }

    @Bean
    @Scope("prototype")
    public Pane pane() {
        return new Pane();
    }

    @Bean
    @Scope("prototype")
    public Board board() {
        return new Board();
    }
}
