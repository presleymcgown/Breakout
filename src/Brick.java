import acm.graphics.GRect;
import java.awt.Color;

public class Brick extends GRect {

    public static final int WIDTH = 44;
    public static final int HEIGHT = 20;
    public Color color;
    public int lives;
    public boolean life;
    public int randomPowerup;
    public boolean powerUp;

    public Brick(int x, int y, Color color, int row, int lives, boolean life, int randomPowerup) {

        super(x, y, WIDTH, HEIGHT);

        powerUp = false;
        this.randomPowerup = randomPowerup;
        this.lives = lives;
        this.life = life;
        this.color = color;
        this.setFillColor(color);
        this.setFilled(true);

    }
}
