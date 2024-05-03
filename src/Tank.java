import java.util.Random;

public class Tank extends Entity{
    public Tank(double[] initialCoords) {
        super(initialCoords);

        // Initialize the radius to a random number between 10 and 45
        // Formula -> rand.nextInt( (max - min) + 1 ) + min
        Random rand = new Random();
        radius = rand.nextInt(36) + 10;

        damage = 50;
    }

    public void updatePosition(double deltaTime) {
        coordinates[0] -= dx * deltaTime;
    }

    public void teleport() {
        // Generate 2 random integers between -30 and 30 for x and y coords
        int xRand = new Random().nextInt(61) - 30;
        int yRand = new Random().nextInt(61) - 30;

        coordinates[0] += xRand;
        coordinates[1] += yRand;
    }
}
