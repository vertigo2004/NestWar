package sample;

import javafx.scene.paint.Color;

/**
 * Created by vertigo on 2/23/16.
 */
public enum Player {
    RED(Color.web("red", 0.55)),
    BLUE(Color.web("blue", 0.55)),
    GREY(Color.web("white", 0.15));

    Color color;
    Player(Color color) {
        this. color = color;
    }
}
