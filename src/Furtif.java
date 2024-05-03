import java.lang.Math;
import java.util.Random;

public class Furtif extends Entity {
    double initialY;

    public Furtif(double[] initialCoords, int radius) {
        super(initialCoords, radius);
        initialY = initialCoords[1];

        // Initialize the radius to a random number between 10 and 45
        // Formula -> rand.nextInt( (max - min) + 1 ) + min
        Random rand = new Random();
        radius = rand.nextInt(36) + 10;

        damage = 0;
    }

    @Override
    public void updatePosition(double deltaTime) {
        coordinates[0] -= dx * deltaTime;

        // Sine wave formula A * sin(x) + D
        // A -> Amplitude (50px)
        // D -> Vertical shift (center wave at initial y coordinate)
        dy = 50 * Math.sin(Math.toRadians(coordinates[0])) + initialY;
        coordinates[1] = dy;
    }
}
