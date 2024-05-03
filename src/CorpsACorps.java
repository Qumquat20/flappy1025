import java.util.Random;

public class CorpsACorps extends Entity{
    public CorpsACorps(double[] initialCoords) {
        super(initialCoords);

        // Initialize the radius to a random number between 10 and 45
        // Formula -> rand.nextInt( (max - min) + 1 ) + min
        Random rand = new Random();
        radius = rand.nextInt(36) + 10;

        damage = 100;
    }

    public void updatePosition(double deltaTime) {
        coordinates[0] -= dx * deltaTime;
    }
}
