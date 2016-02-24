package sample;

/**
 * Created by vertigo on 2/22/16.
 */
public enum NestSize {
    SMALL(30, 1),
    MEDIUM(50, 2),
    BIG(70, 3);

    private int radius;
    private int increment;

    NestSize(int r, int inc) {
        this.radius = r;
        this.increment = inc;
    }

    public int getRadius() {
        return radius;
    }

    public int getIncrement() {
        return increment;
    }
}
